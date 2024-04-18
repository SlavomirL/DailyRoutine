package com.slavomirlobotka.dailyroutineforkids.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
@Entity
public class ScheduleTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @NotNull
    private Integer points;

    @NotNull
    private Boolean mustBeDone;

}