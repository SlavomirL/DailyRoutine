package com.slavomirlobotka.dailyroutineforkids.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {

  private String status;
  private String error;
  private String timestamp;
}
