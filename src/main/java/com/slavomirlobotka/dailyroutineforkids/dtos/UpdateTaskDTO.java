package com.slavomirlobotka.dailyroutineforkids.dtos;

import lombok.Data;

@Data
public class UpdateTaskDTO {
  private String taskName;
  private String description;
}
