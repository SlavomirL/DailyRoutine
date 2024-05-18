package com.slavomirlobotka.dailyroutineforkids.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisplayChildDTO {

  private Long id;
  private String name;
  private Integer age;
  private String gender;
  private List<ScheduleResponseDTO> schedules;
}
