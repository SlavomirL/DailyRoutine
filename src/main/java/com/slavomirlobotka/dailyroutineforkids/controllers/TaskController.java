package com.slavomirlobotka.dailyroutineforkids.controllers;

import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.ScheduleTask;
import com.slavomirlobotka.dailyroutineforkids.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class TaskController {

  private final TaskService taskService;

  @PostMapping(value = "/tasks/{taskName}", consumes = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<?> createNewTask(
      @PathVariable String taskName, @RequestBody(required = false) String description)
      throws DailyRoutineBadRequest {
    if (description == null || description.isEmpty()) {
      taskService.createCustomTask(taskName);
    } else {
      taskService.createCustomTask(taskName, description);
    }

    return ResponseEntity.ok("Task '" + taskName + "' created.");
  }
}
