package com.slavomirlobotka.dailyroutineforkids.controllers;

import com.slavomirlobotka.dailyroutineforkids.dtos.UpdateScheduleTaskDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.ScheduleTask;
import com.slavomirlobotka.dailyroutineforkids.services.ScheduleTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScheduleTaskController {

  private final ScheduleTaskService scheduleTaskService;

  @PatchMapping("/schedule-tasks/{sTaskId}")
  public ResponseEntity<?> modifyScheduleTask(
      @PathVariable Long sTaskId, @RequestBody UpdateScheduleTaskDTO updateSTask)
      throws DailyRoutineNotFound {

    ScheduleTask sTask = scheduleTaskService.updateTaskAttributes(sTaskId, updateSTask);

    return ResponseEntity.ok(
        "Task updated successfully. Points value: '"
            + sTask.getPoints()
            + "'. Has to be done: '"
            + sTask.getMustBeDone()
            + "'. Is finished: '"
            + sTask.getIsFinished()
            + "'.");
  }

  @DeleteMapping("/schedule-tasks/{sTaskId}")
  public ResponseEntity<?> removeScheduleTask(@PathVariable Long sTaskId)
      throws DailyRoutineNotFound {

    ScheduleTask sTask = scheduleTaskService.removeScheduleTask(sTaskId);

    return ResponseEntity.ok(
        "Task '"
            + sTask.getTask().getTaskName()
            + "' deleted successfully from the schedule '"
            + sTask.getSchedule().getScheduleName()
            + "' of child '"
            + sTask.getSchedule().getChild().getName()
            + "'.");
  }
}
