package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.UpdateTaskDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.Task;

public interface TaskService {
  void createCustomTask(String taskName, String description) throws DailyRoutineBadRequest;

  void createCustomTask(String taskName) throws DailyRoutineBadRequest;

  Task updateTask(Long taskId, UpdateTaskDTO updateTaskDTO)
      throws DailyRoutineNotFound, DailyRoutineBadRequest;
}
