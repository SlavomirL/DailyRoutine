package com.slavomirlobotka.dailyroutineforkids.init;

import com.slavomirlobotka.dailyroutineforkids.models.Task;
import com.slavomirlobotka.dailyroutineforkids.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TaskInitializer implements CommandLineRunner {

  private final TaskRepository taskRepository;

  @Override
  public void run(String... args) {
    initializeTask("Food", "Eat the meal.");
    initializeTask("Teeth", "Brush teeth properly.");
    initializeTask("Clothes", "Get dressed.");
    initializeTask("Homework", "Do the school homework.");
    initializeTask("Toys", "Put toys to their designated place.");
    initializeTask("Room", "Tidy up children`s room.");
    initializeTask("Laundry", "Put dirty clothes to the laundry basket.");
    initializeTask("Play", "Play with sibling.");
    initializeTask("Hoover", "Vacuum the designated rooms.");
    initializeTask("Dishes", "Wash and dry dishes.");
    initializeTask("Trash", "Take out the trash.");
    initializeTask("Grocery", "Go and buy grocery.");
  }

  private void initializeTask(String taskName, String description) {
    if (!taskRepository.existsByTaskName(taskName)) {
      Task task = new Task();
      task.setTaskName(taskName);
      task.setDescription(description);
      task.setIsCustom(false);
      taskRepository.save(task);
    }
  }
}
