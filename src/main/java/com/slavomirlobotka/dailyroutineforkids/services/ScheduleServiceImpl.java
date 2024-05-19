package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.NewScheduleDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.ScheduleListDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.ScheduleResponseDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.Child;
import com.slavomirlobotka.dailyroutineforkids.models.Schedule;
import com.slavomirlobotka.dailyroutineforkids.models.User;
import com.slavomirlobotka.dailyroutineforkids.repositories.ChildRepository;
import com.slavomirlobotka.dailyroutineforkids.repositories.ScheduleRepository;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScheduleServiceImpl implements ScheduleService {

  private final ChildRepository childRepository;
  private final ScheduleRepository scheduleRepository;
  private final AuthenticationService authenticationService;

  @Override
  public void addNewSchedule(Long childId, NewScheduleDTO newScheduleDTO)
      throws DailyRoutineNotFound, DailyRoutineBadRequest {

    Child child = retreiveChild(childId);

    if (scheduleRepository.existsByChildIdAndScheduleName(
        childId, newScheduleDTO.getScheduleName())) {
      throw new DailyRoutineBadRequest(
          "Schedule with this name already exists for child '" + child.getName() + "'.");
    }

    Schedule schedule =
        Schedule.builder()
            .child(child)
            .scheduleName(newScheduleDTO.getScheduleName())
            .weekDays(newScheduleDTO.getWeekDays())
            .build();

    scheduleRepository.save(schedule);
  }

  @Override
  public ScheduleListDTO displayChildSchedules(Long childId) throws DailyRoutineNotFound {
    Child child = retreiveChild(childId);

    List<Schedule> schedules = scheduleRepository.findByChild(child);
    if (schedules.isEmpty()) {
      throw new DailyRoutineNotFound("No schedule for child '" + child.getName() + "' found.");
    }

    ScheduleListDTO result = new ScheduleListDTO();
    result.setChild(child.getName());

    schedules.stream()
        .map(
            s ->
                ScheduleResponseDTO.builder()
                    .scheduleId(s.getId())
                    .scheduleName(s.getScheduleName())
                    .weekDays(s.getWeekDays())
                    .tasks(s.getScheduleTasks())
                    .build())
        .forEach(result::addSchedule);

    return result;
  }

  @Override
  public ScheduleResponseDTO displayScheduleByName(Long childId, String scheduleName)
      throws DailyRoutineNotFound {
    Child child = retreiveChild(childId);

    List<Schedule> schedules = scheduleRepository.findByChild(child);
    if (schedules.isEmpty()) {
      throw new DailyRoutineNotFound(
          "No schedule for child '"
              + child.getName()
              + "' with name '"
              + scheduleName
              + "' found.");
    }

    for (Schedule s : schedules) {
      if (s.getScheduleName().equals(scheduleName)) {

        return ScheduleResponseDTO.builder()
            .child(s.getChild().getName())
            .scheduleId(s.getId())
            .scheduleName(s.getScheduleName())
            .weekDays(s.getWeekDays())
            .tasks(s.getScheduleTasks())
            .build();
      }
    }
    throw new DailyRoutineNotFound(
        "No schedule for child " + child.getName() + " with name '" + scheduleName + "' found.");
  }

  @Override
  public Child retreiveChild(Long childId) throws DailyRoutineNotFound {
    return childRepository
        .findById(childId)
        .orElseThrow(() -> new DailyRoutineNotFound("Child with id '" + childId + "' not found."));
  }

  @Override
  public Schedule modifySchedule(Long childId, Long scheduleId, NewScheduleDTO scheduleData)
      throws DailyRoutineNotFound {
    Child child = retreiveChild(childId);

    Schedule schedule = scheduleRepository.findByChildIdAndId(childId, scheduleId);
    if (schedule == null) {
      throw new DailyRoutineNotFound(
          "No schedule with id '" + scheduleId + "' for child '" + child.getName() + "' found.");
    }

    if (scheduleData.getScheduleName() != null) {
      schedule.setScheduleName(scheduleData.getScheduleName());
    }
    if (scheduleData.getWeekDays() != null) {
      schedule.setWeekDays(scheduleData.getWeekDays());
    }
    //    if (scheduleData.getTasks() != null) {
    //      schedule.setTasks(scheduleData.getTasks());
    //    }
    return scheduleRepository.save(schedule);
  }

  @Override
  public List<String> addSameScheduleToAll(NewScheduleDTO newScheduleDTO)
      throws DailyRoutineNotFound, DailyRoutineBadRequest {
    User user = authenticationService.getCurrentParent();
    List<Child> children = childRepository.findAllByUser(user);
    if (children == null || children.isEmpty()) {
      throw new DailyRoutineNotFound(
          "There are no children belonging to parent '" + user.getFirstName() + "'.");
    }

    List<String> childrenWithExistingSchedule = new ArrayList<>();
    for (Child ch : children) {
      if (!scheduleRepository.existsByChildIdAndScheduleName(
          ch.getId(), newScheduleDTO.getScheduleName())) {
        Schedule schedule =
            Schedule.builder()
                .child(ch)
                .scheduleName(newScheduleDTO.getScheduleName())
                .weekDays(newScheduleDTO.getWeekDays())
                .build();

        scheduleRepository.save(schedule);
      } else {
        childrenWithExistingSchedule.add(ch.getName());
      }
    }

    if (childrenWithExistingSchedule.size() == children.size()) {
      throw new DailyRoutineBadRequest(
          "The schedule with name '"
              + newScheduleDTO.getScheduleName()
              + "' already exists for all children belonging to parent '"
              + user.getFirstName()
              + "'.");
    }

    return childrenWithExistingSchedule;
  }

  @Override
  public Child removeSchedule(Long childId, Long scheduleId) throws DailyRoutineNotFound {
    Child child = retreiveChild(childId);

    Schedule schedule = scheduleRepository.findByChildIdAndId(childId, scheduleId);
    if (schedule == null) {
      throw new DailyRoutineNotFound(
          "Schedule with ID '"
              + scheduleId
              + "' does not exist for child '"
              + child.getName()
              + "'.");
    }

    scheduleRepository.delete(schedule);

    return child;
  }

  @Override
  @Transactional
  public Child removeAllSchedulesPerChild(Long childId) throws DailyRoutineNotFound {
    Child child = retreiveChild(childId);

    List<Schedule> schedules = scheduleRepository.findByChild(child);
    if (schedules == null || schedules.isEmpty()) {
      throw new DailyRoutineNotFound("No schedules found for child '" + child.getName() + "'.");
    }
    scheduleRepository.deleteAllByChildId(childId);

    return child;
  }
}
