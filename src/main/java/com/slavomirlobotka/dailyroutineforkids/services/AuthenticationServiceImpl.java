package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.config.JwtService;
import com.slavomirlobotka.dailyroutineforkids.dtos.AuthenticationResponseDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.LoginRequestDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterRequestDTO;
import com.slavomirlobotka.dailyroutineforkids.email.EmailConfirmationToken;
import com.slavomirlobotka.dailyroutineforkids.email.EmailConfirmationTokenRepository;
import com.slavomirlobotka.dailyroutineforkids.email.EmailService;
import com.slavomirlobotka.dailyroutineforkids.models.User;
import com.slavomirlobotka.dailyroutineforkids.models.roles.RoleEnum;
import com.slavomirlobotka.dailyroutineforkids.repositories.UserRepository;
import jakarta.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;
  private final EmailConfirmationTokenRepository tokenRepository;
  private final EmailService emailService;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private static final int ACTIVATION_CODE_LENGTH = 6;

  @Transactional
  @Override
  public void registerNewParent(RegisterRequestDTO registerDto) throws Exception {
    User user =
        User.builder()
            .firstName(registerDto.getFirstName())
            .surname(registerDto.getSurname())
            .email(registerDto.getEmail())
            .password(passwordEncoder.encode(registerDto.getPassword()))
            .enabled(false)
            .role(RoleEnum.PARENT)
            .build();

    userRepository.save(user);
    var newToken = generateAndSaveActivationToken(user);
    emailService.sendEmail(user.getEmail(), user.getFirstName(), newToken);
  }

  @Override
  public String generateAndSaveActivationToken(User user) {
    String generatedToken = generateActivationCode(ACTIVATION_CODE_LENGTH);
    var token =
        EmailConfirmationToken.builder()
            .token(generatedToken)
            .createdAt(LocalDateTime.now())
            .expiresAt(LocalDateTime.now().plusMinutes(10))
            .user(user)
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
  public boolean verifyUserCode(String code, String email) throws Exception {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new Exception("User not found.");
    }
    if (user.isEnabled()) {
      throw new Exception("Email already verified and user enabled.");
    }

    EmailConfirmationToken token = tokenRepository.findByToken(code);
    if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
      throw new Exception("Code expired.");
    }
    if (!token.getToken().equals(code)) {
      throw new Exception("Invalid verification code.");
    }

    token.setValidatedAt(LocalDateTime.now());
    user.setEnabled(true);
    userRepository.save(user);
    tokenRepository.save(token);

    return true;
  }

  @Override
  public AuthenticationResponseDTO authenticate(LoginRequestDTO loginRequestDTO) throws Exception {
    if (isUserEnabled(loginRequestDTO.getEmail())) {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwtToken = jwtService.generateToken(authentication);

      return AuthenticationResponseDTO.builder().token(jwtToken).build();
    } else {
      throw new Exception("you have to verify your email first");
    }
  }

  @Override
  public boolean isUserEnabled(String email) {
    User user = userRepository.findByEmail(email);

    return user.isEnabled();
  }
}
