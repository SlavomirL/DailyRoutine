package com.slavomirlobotka.dailyroutineforkids.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DailyRoutineBadRequest extends Exception {
  public DailyRoutineBadRequest(String message) {
    super(message);
  }
}
