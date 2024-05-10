package com.slavomirlobotka.dailyroutineforkids.dtos;

import lombok.Data;

@Data
public class UpdateChildDTO {
  private String name;
  private Integer age;
  private String gender;
}
