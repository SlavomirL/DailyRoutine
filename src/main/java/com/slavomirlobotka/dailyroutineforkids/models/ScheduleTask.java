package com.slavomirlobotka.dailyroutineforkids.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ScheduleTask {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "schedule_id")
  @JsonIgnore
  private Schedule schedule;

  @ManyToOne
  @JoinColumn(name = "task_id")
  private Task task;

  @NotNull private Integer points;

  @NotNull private Boolean mustBeDone;

  @NotNull private Boolean isFinished;

  @Builder
  public ScheduleTask(Schedule schedule, Task task) {
    this.schedule = schedule;
    this.task = task;
    this.points = 5;
    this.mustBeDone = true;
    this.isFinished = false;
  }
}
