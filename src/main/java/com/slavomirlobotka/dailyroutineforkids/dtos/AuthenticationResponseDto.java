package com.slavomirlobotka.dailyroutineforkids.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseDto {
  private String token;
}
