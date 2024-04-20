package com.slavomirlobotka.dailyroutineforkids.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String taskName;

    @OneToMany(mappedBy = "task")
    private List<ScheduleTask> scheduleTasks;

}