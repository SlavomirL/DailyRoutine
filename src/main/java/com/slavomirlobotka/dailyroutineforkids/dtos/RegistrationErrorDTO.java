package com.slavomirlobotka.dailyroutineforkids.dtos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import lombok.*;

@Getter
@Setter
public class RegistrationErrorDTO {
  private String status;
  private Map<String, String> errors;
  private String timestamp;

  public RegistrationErrorDTO(
      String status, LocalDateTime rawTimestamp, Map<String, String> errors) {
    this.status = status;
    this.timestamp =
        rawTimestamp.format(DateTimeFormatter.ofPattern("dd.MMM yyyy, HH:mm:ss", Locale.ENGLISH));
    this.errors = errors;
  }
}
