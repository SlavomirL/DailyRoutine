package com.slavomirlobotka.dailyroutineforkids.controllers;

import com.slavomirlobotka.dailyroutineforkids.dtos.AuthenticationResponseDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.LoginRequestDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterRequestDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineForbidden;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineIO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineUnauthorized;
import com.slavomirlobotka.dailyroutineforkids.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/users/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO)
      throws DailyRoutineIO, DailyRoutineForbidden {
    authenticationService.registerNewParent(registerRequestDTO);

    return ResponseEntity.accepted()
        .body(
            String.format(
                "Dear %s %s, to complete your registration, please visit your email to verify it.",
                registerRequestDTO.getFirstName(), registerRequestDTO.getSurname()));
  }

  @PostMapping("/users/login")
  public ResponseEntity<?> userLogin(@RequestBody LoginRequestDTO loginRequestDTO)
      throws DailyRoutineUnauthorized, DailyRoutineBadRequest {
    AuthenticationResponseDTO response = authenticationService.authenticate(loginRequestDTO);
    String successMessage = response.getSuccessMessage();
    return ResponseEntity.ok(successMessage);
  }
}
