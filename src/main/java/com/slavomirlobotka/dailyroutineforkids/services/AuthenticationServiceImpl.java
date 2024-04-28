package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterRequestDTO;
import com.slavomirlobotka.dailyroutineforkids.email.EmailConfirmationToken;
import com.slavomirlobotka.dailyroutineforkids.email.EmailConfirmationTokenRepository;
import com.slavomirlobotka.dailyroutineforkids.email.EmailService;
import com.slavomirlobotka.dailyroutineforkids.models.Parent;
import com.slavomirlobotka.dailyroutineforkids.repositories.ParentRepository;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final ParentRepository parentRepository;
  private final EmailConfirmationTokenRepository tokenRepository;
  private final EmailService emailService;
  private static final int ACTIVATION_CODE_LENGTH = 6;

  @Override
  public void registerNewParent(RegisterRequestDTO registerDto) throws IOException {
    Parent parent =
        Parent.builder()
            .firstName(registerDto.getFirstName())
            .surname(registerDto.getSurname())
            .email(registerDto.getEmail())
            .password(registerDto.getPassword())
            .enabled(false)
            .build();

    parentRepository.save(parent);
    var newToken = generateAndSaveActivationToken(parent);
    emailService.sendEmail(parent.getEmail(), parent.getFirstName(), newToken);
  }

  @Override
  public String generateAndSaveActivationToken(Parent parent) {
    String generatedToken = generateActivationCode(ACTIVATION_CODE_LENGTH);
    var token =
        EmailConfirmationToken.builder()
            .token(generatedToken)
            .createdAt(LocalDateTime.now())
            .expiresAt(LocalDateTime.now().plusMinutes(10))
            .parent(parent)
            .build();
    tokenRepository.save(token);
    return generatedToken;
  }

  @Override
  public String generateActivationCode(int length) {
    String characters = "0123456789";
    StringBuilder codeBuilder = new StringBuilder();
    SecureRandom secureRandom = new SecureRandom();
    for (int i = 0; i < length; i++) {
      int randomIndex = secureRandom.nextInt(characters.length());
      codeBuilder.append(characters.charAt(randomIndex));
    }
    return codeBuilder.toString();
  }

  @Override
  public boolean verifyUserCode(String code) throws Exception {
    //    Parent parent = parentRepository.findByEmail(email);
    EmailConfirmationToken token = tokenRepository.findByToken(code);
    //    if (parent == null) {
    //      throw new Exception("User not found.");
    //    }
    if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
      throw new Exception("Code expired.");
    }
    if (!token.getToken().equals(code)) {
      throw new Exception("Invalid verification code.");
    }
    //    parent.setEnabled(true);
    token.setValidatedAt(LocalDateTime.now());
    //    parentRepository.save(parent);
    tokenRepository.save(token);
    return true;
  }
}
