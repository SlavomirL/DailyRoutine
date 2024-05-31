package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.UpdateScheduleTaskDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.Schedule;
import com.slavomirlobotka.dailyroutineforkids.models.ScheduleTask;

public interface ScheduleTaskService {

  ScheduleTask addNewTask(Long scheduleId, Long taskId) throws DailyRoutineNotFound;

  ScheduleTask updateTaskAttributes(Long taskId, UpdateScheduleTaskDTO updateScheduleTaskDTO)
      throws DailyRoutineNotFound, DailyRoutineBadRequest;

  ScheduleTask removeScheduleTask(Long taskId) throws DailyRoutineNotFound;

  ScheduleTask updateTaskIsFinished(Long sTaskId, Boolean isFinished)
      throws DailyRoutineNotFound, DailyRoutineBadRequest;

  boolean checkMustBeDone(Schedule schedule);

  boolean checkPoints(Schedule schedule);

  void checkAfterTaskDelete(Long taskId) throws DailyRoutineNotFound;
}
