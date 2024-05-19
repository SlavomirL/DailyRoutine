package com.slavomirlobotka.dailyroutineforkids.controllers;

import com.slavomirlobotka.dailyroutineforkids.dtos.NewScheduleDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.Child;
import com.slavomirlobotka.dailyroutineforkids.models.Schedule;
import com.slavomirlobotka.dailyroutineforkids.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ScheduleController {

  private final ScheduleService scheduleService;

  @PostMapping("/children/{childId}/schedule")
  public ResponseEntity<?> addScheduleToChild(
      @PathVariable Long childId, @RequestBody NewScheduleDTO newScheduleDTO)
      throws DailyRoutineNotFound, DailyRoutineBadRequest {

    scheduleService.addNewSchedule(childId, newScheduleDTO);

    return ResponseEntity.ok(
        "Schedule '" + newScheduleDTO.getScheduleName() + "' created for child with ID " + childId);
  }

  @GetMapping("/children/{childId}/schedule")
  public ResponseEntity<?> checkChildSchedule(@PathVariable Long childId)
      throws DailyRoutineNotFound {

    return ResponseEntity.ok(scheduleService.displayChildSchedules(childId));
  }

  @GetMapping("/children/{childId}/schedule/{scheduleName}")
  public ResponseEntity<?> getOneChildSchedule(
      @PathVariable Long childId, @PathVariable String scheduleName) throws DailyRoutineNotFound {

    return ResponseEntity.ok(scheduleService.displayScheduleByName(childId, scheduleName));
  }

  @PatchMapping("/children/{childId}/schedule/{scheduleId}")
  public ResponseEntity<?> updateSchedule(
      @PathVariable Long childId,
      @PathVariable Long scheduleId,
      @RequestBody(required = false) NewScheduleDTO scheduleData)
      throws DailyRoutineNotFound {

    Schedule schedule = scheduleService.modifySchedule(childId, scheduleId, scheduleData);

    return ResponseEntity.ok(
        "Schedule with ID '"
            + scheduleId
            + "' modified successfully. New name: '"
            + schedule.getScheduleName()
            + "'. New week days: '"
            + schedule.getWeekDays()
            + "'.");
  }

  @PostMapping("/children/schedule")
  public ResponseEntity<?> addSameSchedule(@RequestBody NewScheduleDTO newScheduleDTO)
      throws DailyRoutineNotFound, DailyRoutineBadRequest {

    List<String> namesWithExisting = scheduleService.addSameScheduleToAll(newScheduleDTO);

    if (namesWithExisting == null || namesWithExisting.isEmpty()) {
      return ResponseEntity.ok(
          "Schedule '" + newScheduleDTO.getScheduleName() + "' created for all children.");
    }

    return ResponseEntity.status(HttpStatusCode.valueOf(209))
        .body(
            "The schedule with name '"
                + newScheduleDTO.getScheduleName()
                + "' already exists for children: '"
                + namesWithExisting
                + "'. It has only been created for other children belonging to this parent.");
  }

  @DeleteMapping("children/{childId}/schedule/{scheduleId}")
  public ResponseEntity<?> deleteSchedule(@PathVariable Long childId, @PathVariable Long scheduleId)
      throws DailyRoutineNotFound {
    Child child = scheduleService.removeSchedule(childId, scheduleId);

    return ResponseEntity.ok(
        "Schedule with id '"
            + scheduleId
            + "' has been removed from the list for child '"
            + child.getName()
            + "'.");
  }
}
