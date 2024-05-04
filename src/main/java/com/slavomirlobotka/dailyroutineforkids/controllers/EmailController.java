package com.slavomirlobotka.dailyroutineforkids.controllers;

import com.slavomirlobotka.dailyroutineforkids.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class EmailController {

  private final AuthenticationService authenticationService;

  @GetMapping("/users/verify")
  public String verificationPage(@RequestParam("email") String email, Model model) {
    model.addAttribute("email", email);

    return "verification-page";
  }

  @PostMapping("/users/verify")
  public ResponseEntity<?> verifyUser(
      @RequestParam("code") String code, @RequestParam("email") String email) {

    try {
      boolean isVerified = authenticationService.verifyUserCode(code, email);
      if (isVerified) {
        return ResponseEntity.ok("Account successfully verified.");
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid code or email.");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
