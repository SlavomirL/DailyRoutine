package com.slavomirlobotka.dailyroutineforkids;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class DailyRoutineForKidsApplication {

  public static void main(String[] args) {
    SpringApplication.run(DailyRoutineForKidsApplication.class, args);
  }
}
