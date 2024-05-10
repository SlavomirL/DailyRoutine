package com.slavomirlobotka.dailyroutineforkids.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DailyRoutineForbidden extends Exception {
  public DailyRoutineForbidden(String message) {
    super(message);
  }
}
