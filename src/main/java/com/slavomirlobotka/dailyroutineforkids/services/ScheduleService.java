package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.NewScheduleDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;

public interface ScheduleService {
  void addNewSchedule(Long childId, NewScheduleDTO newScheduleDTO) throws DailyRoutineNotFound;
}
