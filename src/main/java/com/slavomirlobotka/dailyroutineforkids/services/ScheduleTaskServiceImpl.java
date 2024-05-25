package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.UpdateScheduleTaskDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.Schedule;
import com.slavomirlobotka.dailyroutineforkids.models.ScheduleTask;
import com.slavomirlobotka.dailyroutineforkids.models.Task;
import com.slavomirlobotka.dailyroutineforkids.repositories.ScheduleRepository;
import com.slavomirlobotka.dailyroutineforkids.repositories.ScheduleTaskRepository;
import com.slavomirlobotka.dailyroutineforkids.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScheduleTaskServiceImpl implements ScheduleTaskService {

  private final ScheduleRepository scheduleRepository;
  private final TaskRepository taskRepository;
  private final ScheduleTaskRepository scheduleTaskRepository;

  @Transactional
  @Override
  public ScheduleTask addNewTask(Long scheduleId, Long taskId) throws DailyRoutineNotFound {
    Optional<Schedule> schedule = scheduleRepository.findById(scheduleId);

    if (schedule.isEmpty()) {
      throw new DailyRoutineNotFound("Schedule with id '" + scheduleId + "' not found.");
    }

    Optional<Task> task = taskRepository.findById(taskId);
    if (task.isEmpty()) {
      throw new DailyRoutineNotFound("Task with id '" + taskId + "' not found.");
    }

    ScheduleTask scheduleTask =
        ScheduleTask.builder().schedule(schedule.get()).task(task.get()).build();

    scheduleTaskRepository.save(scheduleTask);

    return scheduleTask;
  }

  @Transactional
  @Override
  public ScheduleTask updateTaskAttributes(Long taskId, UpdateScheduleTaskDTO updateScheduleTaskDTO)
      throws DailyRoutineNotFound {
    ScheduleTask scheduleTask =
        scheduleTaskRepository
            .findById(taskId)
            .orElseThrow(
                () ->
                    new DailyRoutineNotFound(
                        "No task with ID '" + taskId + "' belonging to the schedule was found."));

    if (updateScheduleTaskDTO.getPoints() != null) {
      scheduleTask.setPoints(updateScheduleTaskDTO.getPoints());
    }
    if (updateScheduleTaskDTO.getMustBeDone() != null) {
      scheduleTask.setMustBeDone(updateScheduleTaskDTO.getMustBeDone());
    }
    if (updateScheduleTaskDTO.getIsFinished() != null) {
      scheduleTask.setIsFinished(updateScheduleTaskDTO.getIsFinished());
    }

    scheduleTaskRepository.save(scheduleTask);

    return scheduleTask;
  }

  @Override
  public ScheduleTask removeScheduleTask(Long taskId) throws DailyRoutineNotFound {
    ScheduleTask scheduleTask =
        scheduleTaskRepository
            .findById(taskId)
            .orElseThrow(
                () ->
                    new DailyRoutineNotFound(
                        "No task with ID '" + taskId + "' belonging to the schedule was found."));
    scheduleTaskRepository.delete(scheduleTask);

    return scheduleTask;
  }
}
