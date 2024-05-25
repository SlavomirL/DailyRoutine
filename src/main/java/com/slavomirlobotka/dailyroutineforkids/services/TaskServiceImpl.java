package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.UpdateTaskDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.Task;
import com.slavomirlobotka.dailyroutineforkids.models.User;
import com.slavomirlobotka.dailyroutineforkids.repositories.ScheduleTaskRepository;
import com.slavomirlobotka.dailyroutineforkids.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final ScheduleTaskRepository scheduleTaskRepository;
  private final AuthenticationService authenticationService;

  @Override
  public void createCustomTask(String taskName) throws DailyRoutineBadRequest {
    User user = authenticationService.getCurrentParent();
    if (taskRepository.existsByTaskNameAndUserId(taskName, user.getId())) {
      throw new DailyRoutineBadRequest(
          "Task with name '"
              + taskName
              + "' already exists for parent '"
              + user.getFirstName()
              + "'.");
    }
    Task newTask = Task.builder().taskName(taskName).isCustom(true).user(user).build();
    taskRepository.save(newTask);
  }

  @Override
  public void createCustomTask(String taskName, String description) throws DailyRoutineBadRequest {
    User user = authenticationService.getCurrentParent();
    if (taskRepository.existsByTaskNameAndUserId(taskName, user.getId())) {
      throw new DailyRoutineBadRequest(
          "Task with name '"
              + taskName
              + "' already exists for parent '"
              + user.getFirstName()
              + "'.");
    }
    Task newTask =
        Task.builder()
            .taskName(taskName)
            .isCustom(true)
            .description(description)
            .user(user)
            .build();
    taskRepository.save(newTask);
  }

  @Transactional
  @Override
  public Task updateTask(Long taskId, UpdateTaskDTO updateTaskDTO)
      throws DailyRoutineNotFound, DailyRoutineBadRequest {
    User user = authenticationService.getCurrentParent();
    Task task =
        taskRepository
            .findById(taskId)
            .orElseThrow(() -> new DailyRoutineNotFound("No task with ID '" + taskId + "' found."));

    if (!task.getIsCustom()) {
      throw new DailyRoutineBadRequest("Default Tasks cannot be modified");
    }
    if (task.getUser() == null || !task.getUser().getId().equals(user.getId())) {
      throw new DailyRoutineBadRequest(
          "Custom task with ID '" + taskId + "' belongs to different user and cannot be modified.");
    }
    if (updateTaskDTO.getTaskName() != null) {
      task.setTaskName(updateTaskDTO.getTaskName());
    }
    if (updateTaskDTO.getDescription() != null) {
      task.setDescription(updateTaskDTO.getDescription());
    }
    taskRepository.save(task);

    return task;
  }

  @Transactional
  @Override
  public Task removeTask(Long taskId) throws DailyRoutineNotFound, DailyRoutineBadRequest {
    User user = authenticationService.getCurrentParent();
    Task task =
        taskRepository
            .findById(taskId)
            .orElseThrow(() -> new DailyRoutineNotFound("No task with ID '" + taskId + "' found."));

    if (!task.getIsCustom()) {
      throw new DailyRoutineBadRequest("Default Tasks cannot be deleted");
    }
    if (task.getUser() == null || !task.getUser().getId().equals(user.getId())) {
      throw new DailyRoutineBadRequest(
          "Custom task with ID '" + taskId + "' belongs to different user and cannot be deleted.");
    }
    scheduleTaskRepository.deleteByTaskId(task.getId());
    taskRepository.delete(task);

    return task;
  }
}
