package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.UpdateScheduleTaskDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;
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
    Optional<Schedule> scheduleOpt = scheduleRepository.findById(scheduleId);

    if (scheduleOpt.isEmpty()) {
      throw new DailyRoutineNotFound("Schedule with id '" + scheduleId + "' not found.");
    }

    Optional<Task> task = taskRepository.findById(taskId);
    if (task.isEmpty()) {
      throw new DailyRoutineNotFound("Task with id '" + taskId + "' not found.");
    }

    ScheduleTask scheduleTask =
        ScheduleTask.builder().schedule(scheduleOpt.get()).task(task.get()).build();

    scheduleTaskRepository.save(scheduleTask);

    Schedule schedule = scheduleRepository.findScheduleByScheduleTaskId(scheduleTask.getId());
    schedule.setMaxPoints(schedule.getMaxPoints() + scheduleTask.getPoints());
    schedule.setPointsToFinish(schedule.getPointsToFinish() + scheduleTask.getPoints());
    scheduleRepository.save(schedule);

    return scheduleTask;
  }

  @Transactional
  @Override
  public ScheduleTask updateTaskAttributes(
      Long sTaskId, UpdateScheduleTaskDTO updateScheduleTaskDTO)
      throws DailyRoutineNotFound, DailyRoutineBadRequest {
    ScheduleTask scheduleTask = retreiveScheduleTask(sTaskId);

    Schedule schedule = scheduleRepository.findScheduleByScheduleTaskId(scheduleTask.getId());
    if (updateScheduleTaskDTO.getPoints() != null) {
      int previousPoints = scheduleTask.getPoints();
      int newPoints = updateScheduleTaskDTO.getPoints();
      if (newPoints < 0) {
        throw new DailyRoutineBadRequest(
            "Points assigned to task must be equal or greater than 0.");
      }
      scheduleTask.setPoints(newPoints);
      schedule.setMaxPoints(schedule.getMaxPoints() - previousPoints + newPoints);
      if(schedule.getPointsToFinish() > schedule.getMaxPoints()) {
        schedule.setPointsToFinish(schedule.getMaxPoints());
      }
      scheduleRepository.save(schedule);
    }
    if (updateScheduleTaskDTO.getMustBeDone() != null) {
      scheduleTask.setMustBeDone(updateScheduleTaskDTO.getMustBeDone());
    }

    scheduleTaskRepository.save(scheduleTask);

    return scheduleTask;
  }

  @Transactional
  @Override
  public ScheduleTask updateTaskIsFinished(Long sTaskId, Boolean isFinished)
      throws DailyRoutineNotFound, DailyRoutineBadRequest {
    ScheduleTask scheduleTask = retreiveScheduleTask(sTaskId);

    if (scheduleTask.getIsFinished() && isFinished) {
      throw new DailyRoutineBadRequest("This scheduleTask had already been finished previously.");
    }

    if (!scheduleTask.getIsFinished() && !isFinished) {
      throw new DailyRoutineBadRequest("This scheduleTask is already waiting for completion.");
    }

    Schedule schedule = scheduleRepository.findScheduleByScheduleTaskId(scheduleTask.getId());

    scheduleTask.setIsFinished(isFinished);
    if (isFinished) {
      schedule.setCurrentPoints(schedule.getCurrentPoints() + scheduleTask.getPoints());
      if (schedule.getCurrentPoints() >= schedule.getPointsToFinish()) {
        schedule.setIsFinished(isFinished);
      }
    } else {
      schedule.setCurrentPoints(schedule.getCurrentPoints() - scheduleTask.getPoints());
      if (schedule.getCurrentPoints() < schedule.getPointsToFinish()) {
        schedule.setIsFinished(isFinished);
      }
    }

    scheduleTaskRepository.save(scheduleTask);
    scheduleRepository.save(schedule);
    return scheduleTask;
  }

  @Transactional
  @Override
  public ScheduleTask removeScheduleTask(Long sTaskId) throws DailyRoutineNotFound {
    ScheduleTask scheduleTask = retreiveScheduleTask(sTaskId);

    Schedule schedule = scheduleRepository.findScheduleByScheduleTaskId(scheduleTask.getId());
    schedule.setMaxPoints(schedule.getMaxPoints() - scheduleTask.getPoints());

    if(schedule.getPointsToFinish() > schedule.getMaxPoints()) {
      schedule.setPointsToFinish(schedule.getMaxPoints());
    }

    if(scheduleTask.getIsFinished()) {
      schedule.setCurrentPoints(schedule.getCurrentPoints() - scheduleTask.getPoints());
    }

      schedule.setIsFinished(schedule.getCurrentPoints() >= schedule.getPointsToFinish());

    scheduleTaskRepository.delete(scheduleTask);
    scheduleRepository.save(schedule);

    return scheduleTask;
  }

  private ScheduleTask retreiveScheduleTask(Long sTaskId) throws DailyRoutineNotFound {
    ScheduleTask scheduleTask =
        scheduleTaskRepository
            .findById(sTaskId)
            .orElseThrow(
                () ->
                    new DailyRoutineNotFound(
                        "No scheduleTask with ID '" + sTaskId + "' was found."));

    return scheduleTask;
  }
}
