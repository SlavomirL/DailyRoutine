package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.ScheduleTask;
import jakarta.transaction.Transactional;

public interface ScheduleTaskService {
  @Transactional
  ScheduleTask addNewTask(Long scheduleId, Long taskId) throws DailyRoutineNotFound;
}
