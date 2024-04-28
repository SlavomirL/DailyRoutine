package com.slavomirlobotka.dailyroutineforkids.controllers;

import com.slavomirlobotka.dailyroutineforkids.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class EmailController {

  private final AuthenticationService authenticationService;

  @GetMapping("/users/verify")
  public String verificationPage() {
    return "verification-page";
  }

  @PostMapping("/users/verify")
  public ResponseEntity<String> verifyUser(@RequestParam("code") String code) {
    try {
      boolean isVerified = authenticationService.verifyUserCode(code);
      if (isVerified) {
        return ResponseEntity.ok("Email successfully verified.");
      } else {
        return ResponseEntity.badRequest()
            .body("Verification failed: Invalid code or code expired.");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
    }
  }
}
