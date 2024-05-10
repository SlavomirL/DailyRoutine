package com.slavomirlobotka.dailyroutineforkids.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DailyRoutineUnauthorized extends Exception {
  public DailyRoutineUnauthorized(String message) {
    super(message);
  }
}
