package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;
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
}
