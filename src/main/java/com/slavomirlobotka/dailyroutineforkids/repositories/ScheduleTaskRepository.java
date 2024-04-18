package com.slavomirlobotka.dailyroutineforkids.repositories;

import com.slavomirlobotka.dailyroutineforkids.models.ScheduleTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleTaskRepository extends JpaRepository<ScheduleTask, Long> {

}