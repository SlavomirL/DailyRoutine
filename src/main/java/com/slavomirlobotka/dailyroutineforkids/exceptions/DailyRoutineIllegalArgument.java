package com.slavomirlobotka.dailyroutineforkids.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DailyRoutineIllegalArgument extends Exception {
  public DailyRoutineIllegalArgument(String message) {
    super(message);
  }
}
