package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.NewScheduleDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.ScheduleListDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.ScheduleResponseDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.Child;
import com.slavomirlobotka.dailyroutineforkids.models.Schedule;
import com.slavomirlobotka.dailyroutineforkids.repositories.ChildRepository;
import com.slavomirlobotka.dailyroutineforkids.repositories.ScheduleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScheduleServiceImpl implements ScheduleService {

  private final ChildRepository childRepository;
  private final ScheduleRepository scheduleRepository;

  @Override
  public void addNewSchedule(Long childId, NewScheduleDTO newScheduleDTO)
      throws DailyRoutineNotFound {

    Child child = retreiveChild(childId);

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
      throw new DailyRoutineNotFound("No schedule for child " + child.getName() + " found.");
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
          "No schedule for child " + child.getName() + " with name '" + scheduleName + "' found.");
    }

    for (Schedule s : schedules) {
      if (!s.getScheduleName().equals(scheduleName)) {

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
        .orElseThrow(() -> new DailyRoutineNotFound("Child with id " + childId + " not found."));
  }
}
