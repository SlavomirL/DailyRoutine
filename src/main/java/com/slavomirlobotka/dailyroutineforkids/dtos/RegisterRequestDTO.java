package com.slavomirlobotka.dailyroutineforkids.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
  @NotNull
  @Size(min = 1, max = 255)
  private String firstName;

  @NotNull
  @Size(min = 1, max = 255)
  private String surname;

  @NotNull
  @Email(
      regexp =
          "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
  private String email;

  @NotNull
  @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$")
  private String password;
}
