package com.slavomirlobotka.dailyroutineforkids.dtos;

import lombok.Data;

@Data
public class UpdateScheduleTaskDTO {
  private Integer points;
  private Boolean mustBeDone;
}
