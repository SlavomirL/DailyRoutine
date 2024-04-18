package com.slavomirlobotka.dailyroutineforkids.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
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
