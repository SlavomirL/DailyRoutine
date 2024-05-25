package com.slavomirlobotka.dailyroutineforkids.controllers;

import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.ScheduleTask;
import com.slavomirlobotka.dailyroutineforkids.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class TaskController {

  private final TaskService taskService;

  @PostMapping("/tasks/{taskName}")
  public ResponseEntity<?> createNewTask(@PathVariable String taskName)
      throws DailyRoutineBadRequest {
    taskService.createCustomTask(taskName);

    return ResponseEntity.ok("Task '" + taskName + "' created.");
  }

  @PostMapping("/schedules/{scheduleId}/tasks/{taskId}")
  public ResponseEntity<?> addTaskToSchedule(
      @PathVariable Long scheduleId, @PathVariable Long taskId) throws DailyRoutineNotFound {
    ScheduleTask added = taskService.addNewTask(scheduleId, taskId);

    return ResponseEntity.ok(
        "Task '"
            + added.getTask().getTaskName()
            + "' added to the schedule '"
            + added.getSchedule().getScheduleName()
            + "' of child '"
            + added.getSchedule().getChild().getName()
            + "'.");
  }
}
