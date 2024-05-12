package com.slavomirlobotka.dailyroutineforkids.controllers;

import com.slavomirlobotka.dailyroutineforkids.dtos.NewScheduleDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ScheduleController {

  private final ScheduleService scheduleService;

  @PostMapping("/child/{childId}/schedule")
  public ResponseEntity<?> addScheduleToChild(
      @PathVariable Long childId, @RequestBody(required = false) NewScheduleDTO newScheduleDTO)
      throws DailyRoutineNotFound {

    scheduleService.addNewSchedule(childId, newScheduleDTO);

    return ResponseEntity.ok(
        "Schedule '" + newScheduleDTO.getScheduleName() + "' created for child with ID " + childId);
  }
}
