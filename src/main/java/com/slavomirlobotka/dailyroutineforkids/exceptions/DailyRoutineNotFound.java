package com.slavomirlobotka.dailyroutineforkids.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DailyRoutineNotFound extends Exception {
  public DailyRoutineNotFound(String message) {
    super(message);
  }
}
