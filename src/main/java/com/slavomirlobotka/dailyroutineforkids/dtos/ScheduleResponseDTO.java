package com.slavomirlobotka.dailyroutineforkids.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.slavomirlobotka.dailyroutineforkids.enums.DayOfWeek;
import com.slavomirlobotka.dailyroutineforkids.models.ScheduleTask;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleResponseDTO {

  private String child;
  private Long scheduleId;
  private String scheduleName;
  private Set<DayOfWeek> weekDays;
  private Integer maxPoints;
  private Integer pointsToFinish;
  private Integer currentPoints;
  private Boolean isFinished;
  private List<ScheduleTask> tasks;
}
