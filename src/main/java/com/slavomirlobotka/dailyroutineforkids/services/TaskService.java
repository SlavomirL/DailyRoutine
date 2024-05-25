package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.ScheduleTask;

public interface TaskService {
  void createCustomTask(String taskName) throws DailyRoutineBadRequest;

  ScheduleTask addNewTask(Long scheduleId, Long taskId) throws DailyRoutineNotFound;
}
