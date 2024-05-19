package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.NewScheduleDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.ScheduleListDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.ScheduleResponseDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.Child;
import com.slavomirlobotka.dailyroutineforkids.models.Schedule;
import java.util.List;

public interface ScheduleService {
  void addNewSchedule(Long childId, NewScheduleDTO newScheduleDTO)
      throws DailyRoutineNotFound, DailyRoutineBadRequest;

  ScheduleListDTO displayChildSchedules(Long childId) throws DailyRoutineNotFound;

  Child retreiveChild(Long childId) throws DailyRoutineNotFound;

  ScheduleResponseDTO displayScheduleByName(Long childId, String scheduleName)
      throws DailyRoutineNotFound;

  Schedule modifySchedule(Long childId, Long scheduleId, NewScheduleDTO scheduleData)
      throws DailyRoutineNotFound;

  List<String> addSameScheduleToAll(NewScheduleDTO newScheduleDTO)
      throws DailyRoutineNotFound, DailyRoutineBadRequest;

  Child removeSchedule(Long childId, Long scheduleId) throws DailyRoutineNotFound;
}
