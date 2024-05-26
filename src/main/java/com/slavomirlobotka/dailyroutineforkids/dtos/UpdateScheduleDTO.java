package com.slavomirlobotka.dailyroutineforkids.dtos;

import com.slavomirlobotka.dailyroutineforkids.enums.DayOfWeek;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class UpdateScheduleDTO {

  private String scheduleName;
  private Set<DayOfWeek> weekDays = new HashSet<>();
  private Integer pointsToFinish;
}
