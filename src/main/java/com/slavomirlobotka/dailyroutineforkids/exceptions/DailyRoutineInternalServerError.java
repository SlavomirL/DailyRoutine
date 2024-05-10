package com.slavomirlobotka.dailyroutineforkids.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DailyRoutineInternalServerError extends Exception {
  public DailyRoutineInternalServerError(String message) {
    super(message);
  }
}
