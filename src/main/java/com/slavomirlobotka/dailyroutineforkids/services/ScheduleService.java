package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.NewScheduleDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.ScheduleListDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.Child;

public interface ScheduleService {
  void addNewSchedule(Long childId, NewScheduleDTO newScheduleDTO) throws DailyRoutineNotFound;

  ScheduleListDTO displayChildSchedules(Long childId) throws DailyRoutineNotFound;

  Child retreiveChild(Long childId) throws DailyRoutineNotFound;
}
