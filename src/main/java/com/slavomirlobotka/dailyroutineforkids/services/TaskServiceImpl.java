package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.UpdateTaskDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.Task;
import com.slavomirlobotka.dailyroutineforkids.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;

  @Override
  public void createCustomTask(String taskName) throws DailyRoutineBadRequest {
    if (!taskRepository.existsByTaskName(taskName)) {
      Task newTask = Task.builder().taskName(taskName).custom(true).build();
      taskRepository.save(newTask);
    } else {
      throw new DailyRoutineBadRequest("Task with name '" + taskName + "' already exists.");
    }
  }

  @Override
  public void createCustomTask(String taskName, String description) throws DailyRoutineBadRequest {
    if (!taskRepository.existsByTaskName(taskName)) {
      Task newTask =
          Task.builder().taskName(taskName).custom(true).description(description).build();
      taskRepository.save(newTask);
    } else {
      throw new DailyRoutineBadRequest("Task with name '" + taskName + "' already exists.");
    }
  }

  @Override
  public Task updateTask(Long taskId, UpdateTaskDTO updateTaskDTO)
      throws DailyRoutineNotFound, DailyRoutineBadRequest {
    Task task =
        taskRepository
            .findById(taskId)
            .orElseThrow(() -> new DailyRoutineNotFound("No task with ID '" + taskId + "' found."));

    if(!task.getCustom()) {
      throw new DailyRoutineBadRequest("Default Tasks cannot be modified");
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
}
