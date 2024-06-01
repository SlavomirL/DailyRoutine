package com.slavomirlobotka.dailyroutineforkids.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseDTO {
  private String name;
  private String token;

  public String getSuccessMessage() {
    return "The user \"" + name + "\" successfully logged in. Token: \"" + token + "\".";
  }
}
