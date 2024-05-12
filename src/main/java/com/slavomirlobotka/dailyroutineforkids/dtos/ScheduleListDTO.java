package com.slavomirlobotka.dailyroutineforkids.dtos;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ScheduleListDTO {
  private String child;
  private List<ScheduleResponseDTO> schedules = new ArrayList<>();

  public void addSchedule(ScheduleResponseDTO schedule) {
    schedules.add(schedule);
  }
}
