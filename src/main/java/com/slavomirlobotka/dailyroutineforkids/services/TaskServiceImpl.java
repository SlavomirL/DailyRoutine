package com.slavomirlobotka.dailyroutineforkids.services;

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
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final ScheduleRepository scheduleRepository;
  private final ScheduleTaskRepository scheduleTaskRepository;

  @Override
  public void createCustomTask(String taskName) throws DailyRoutineBadRequest {
    if (!taskRepository.existsByTaskName(taskName)) {
      Task newTask = Task.builder().taskName(taskName).build();
      taskRepository.save(newTask);
    } else {
      throw new DailyRoutineBadRequest("Task with name '" + taskName + "' already exists.");
    }
  }

  @Override
  public void createCustomTask(String taskName, String description) throws DailyRoutineBadRequest {
    if (!taskRepository.existsByTaskName(taskName)) {
      Task newTask = Task.builder().taskName(taskName).description(description).build();
      taskRepository.save(newTask);
    } else {
      throw new DailyRoutineBadRequest("Task with name '" + taskName + "' already exists.");
    }
  }

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
}
