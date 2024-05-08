package com.slavomirlobotka.dailyroutineforkids.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
  private String firstName;
  private String email;
  private String password;
}
