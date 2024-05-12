package com.slavomirlobotka.dailyroutineforkids.models;

import com.slavomirlobotka.dailyroutineforkids.enums.DayOfWeek;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Schedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull private String scheduleName;

  @ElementCollection(targetClass = DayOfWeek.class)
  @CollectionTable(name = "schedule_days", joinColumns = @JoinColumn(name = "schedule_id"))
  @Column(name = "day_of_week")
  @Enumerated(EnumType.STRING)
  private Set<DayOfWeek> weekDays = new HashSet<>();

  @ManyToOne private Child child;

  @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
  private List<ScheduleTask> scheduleTasks;
}
