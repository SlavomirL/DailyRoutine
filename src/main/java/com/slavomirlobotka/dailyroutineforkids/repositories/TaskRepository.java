package com.slavomirlobotka.dailyroutineforkids.repositories;

import com.slavomirlobotka.dailyroutineforkids.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}