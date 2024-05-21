package com.slavomirlobotka.dailyroutineforkids.dtos;

import com.slavomirlobotka.dailyroutineforkids.enums.DayOfWeek;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class NewScheduleDTO {

  private String scheduleName;
  private Set<DayOfWeek> weekDays = new HashSet<>();
  //    private List<TaskDTO> tasks = new ArrayList<>();
  private Integer pointsToFinish;
}
