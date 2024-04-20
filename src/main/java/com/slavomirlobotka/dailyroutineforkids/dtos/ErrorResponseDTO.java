package com.slavomirlobotka.dailyroutineforkids.dtos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.*;

@Getter
@Setter
public class ErrorResponseDTO {
  private String status;
  private String timestamp;
  private Map<String, String> errors;

  public ErrorResponseDTO(String status, LocalDateTime rawTimestamp, Map<String, String> errors) {
    this.status = status;
    this.timestamp = rawTimestamp.format(DateTimeFormatter.ofPattern("dd.MMM.yyyy, HH:mm:ss"));
    this.errors = errors;
  }
}
