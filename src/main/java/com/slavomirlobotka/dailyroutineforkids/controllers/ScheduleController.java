package com.slavomirlobotka.dailyroutineforkids.controllers;

import com.slavomirlobotka.dailyroutineforkids.dtos.NewScheduleDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ScheduleController {

  private final ScheduleService scheduleService;

  @PostMapping("/child/{childId}/schedule")
  public ResponseEntity<?> addScheduleToChild(
      @PathVariable Long childId, @RequestBody NewScheduleDTO newScheduleDTO)
      throws DailyRoutineNotFound, DailyRoutineBadRequest {

    scheduleService.addNewSchedule(childId, newScheduleDTO);

    return ResponseEntity.ok(
        "Schedule '" + newScheduleDTO.getScheduleName() + "' created for child with ID " + childId);
  }

  @GetMapping("/child/{childId}/schedule")
  public ResponseEntity<?> checkChildSchedule(@PathVariable Long childId)
      throws DailyRoutineNotFound {

    return ResponseEntity.ok(scheduleService.displayChildSchedules(childId));
  }

  @GetMapping("/child/{childId}/schedule/{scheduleName}")
  public ResponseEntity<?> getOneChildSchedule(
      @PathVariable Long childId, @PathVariable String scheduleName) throws DailyRoutineNotFound {

    return ResponseEntity.ok(scheduleService.displayScheduleByName(childId, scheduleName));
  }
}
