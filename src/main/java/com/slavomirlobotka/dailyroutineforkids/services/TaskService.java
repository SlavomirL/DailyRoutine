package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;

public interface TaskService {
  void createCustomTask(String taskName, String description) throws DailyRoutineBadRequest;

  void createCustomTask(String taskName) throws DailyRoutineBadRequest;
}
