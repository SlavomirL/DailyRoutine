package com.slavomirlobotka.dailyroutineforkids.models;

import com.slavomirlobotka.dailyroutineforkids.enums.DayOfWeek;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "schedule",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"child_id", "schedule_name"})})
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

  @NotNull private Boolean isFinished;

  @NotNull private Integer maxPoints;

  @NotNull private Integer pointsToFinish;

  @ManyToOne private Child child;

  @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
  private List<ScheduleTask> scheduleTasks;

  @Builder
  public Schedule(String scheduleName, Child child, Set<DayOfWeek> weekDays) {
    this.scheduleName = scheduleName;
    this.child = child;
    this.weekDays = weekDays;
    this.isFinished = false;
    this.maxPoints = 0;
    this.pointsToFinish = 0;
  }
}
