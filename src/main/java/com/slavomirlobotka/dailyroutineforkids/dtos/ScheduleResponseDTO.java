package com.slavomirlobotka.dailyroutineforkids.dtos;

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
public class ScheduleResponseDTO {

  private String scheduleName;
  private Set<DayOfWeek> weekDays;
  private List<ScheduleTask> tasks;
}
