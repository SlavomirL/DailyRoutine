package com.slavomirlobotka.dailyroutineforkids.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull private String taskName;

  private String description;

  @OneToMany(mappedBy = "task")
  private List<ScheduleTask> scheduleTasks;
}
