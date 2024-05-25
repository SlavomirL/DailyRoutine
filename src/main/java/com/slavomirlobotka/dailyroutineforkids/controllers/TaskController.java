package com.slavomirlobotka.dailyroutineforkids.controllers;

import com.slavomirlobotka.dailyroutineforkids.dtos.UpdateTaskDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.ScheduleTask;
import com.slavomirlobotka.dailyroutineforkids.models.Task;
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

  @PatchMapping("/tasks/{taskId}")
  public ResponseEntity<?> modifyTask(
      @PathVariable Long taskId, @RequestBody(required = false) UpdateTaskDTO updateTaskDTO)
      throws DailyRoutineNotFound, DailyRoutineBadRequest {
    Task task = taskService.updateTask(taskId, updateTaskDTO);
    if (updateTaskDTO.getDescription() != null && updateTaskDTO.getTaskName() != null) {
      return ResponseEntity.ok(
          "Task updated. New name: '"
              + task.getTaskName()
              + "'. New description: '"
              + task.getDescription()
              + "'.");
    } else if (updateTaskDTO.getDescription() != null) {
      return ResponseEntity.ok("Task updated. New description: '" + task.getDescription() + "'.");
    } else if (updateTaskDTO.getTaskName() != null) {
      return ResponseEntity.ok("Task updated. New name: '" + task.getTaskName() + "'.");
    } else {
      return ResponseEntity.ok("No changes for the task '" + task.getTaskName() + "'.");
    }
  }
}
