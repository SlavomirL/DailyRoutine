package com.slavomirlobotka.dailyroutineforkids.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DailyRoutineIO extends Exception {
  public DailyRoutineIO(String message) {
    super(message);
  }
}
