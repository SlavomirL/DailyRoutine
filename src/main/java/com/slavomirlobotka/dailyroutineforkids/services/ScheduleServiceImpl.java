package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.NewScheduleDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.Child;
import com.slavomirlobotka.dailyroutineforkids.models.Schedule;
import com.slavomirlobotka.dailyroutineforkids.repositories.ChildRepository;
import com.slavomirlobotka.dailyroutineforkids.repositories.ScheduleRepository;
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

    Child child =
        childRepository
            .findById(childId)
            .orElseThrow(
                () -> new DailyRoutineNotFound("Child with id " + childId + " not found."));

    Schedule schedule =
        Schedule.builder()
            .child(child)
            .scheduleName(newScheduleDTO.getScheduleName())
            .weekDays(newScheduleDTO.getWeekDays())
            .build();

    scheduleRepository.save(schedule);
  }
}
