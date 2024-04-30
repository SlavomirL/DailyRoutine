package com.slavomirlobotka.dailyroutineforkids.controllers;

import com.slavomirlobotka.dailyroutineforkids.dtos.LoginRequestDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterRequestDTO;
import com.slavomirlobotka.dailyroutineforkids.services.AuthenticationService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/users/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO)
      throws Exception {
    authenticationService.registerNewParent(registerRequestDTO);

    return ResponseEntity.accepted()
        .body(
            String.format(
                "Dear %s %s, to complete your registration, please visit your email to verify it.",
                registerRequestDTO.getFirstName(), registerRequestDTO.getSurname()));
  }

  @PostMapping("/users/login")
  public ResponseEntity<?> userLogin(LoginRequestDTO loginRequestDTO) {
    if(loginRequestDTO == null || loginRequestDTO.getPassword() == null || loginRequestDTO.getEmail() == null) {
      return ResponseEntity.badRequest().build();
    }

//    authenticationService.authenticate(loginRequestDTO);
//    return ResponseEntity.ok("Login of user " + loginRequestDTO.getFirstName() + " successful");

    return ResponseEntity.ok(authenticationService.authenticate(loginRequestDTO));
  }
}
