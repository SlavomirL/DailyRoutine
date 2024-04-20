package com.slavomirlobotka.dailyroutineforkids.controllers;

import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterRequestDTO;
import com.slavomirlobotka.dailyroutineforkids.services.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RegistrationController {

  private final RegistrationService registrationService;

  @PostMapping("/users")
  public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
    registrationService.registerNewParent(registerRequestDTO);

    return ResponseEntity.ok("User is valid");
  }
}
