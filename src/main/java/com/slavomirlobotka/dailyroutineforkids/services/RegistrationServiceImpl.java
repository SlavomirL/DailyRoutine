package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterRequestDTO;
import com.slavomirlobotka.dailyroutineforkids.models.Parent;
import com.slavomirlobotka.dailyroutineforkids.repositories.ParentRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

  private final ParentRepository parentRepository;
  private final Validator validator;

  @PostConstruct
  private void postConstruct() {
    if (validator == null) {
      System.out.println("Validator is not injected!");
    } else {
      System.out.println("Validator is injected successfully.");
    }
  }

  public Optional<Parent> registerNewParent(RegisterRequestDTO registerDto) {
    System.out.println("start of service method invocation");
    Set<ConstraintViolation<RegisterRequestDTO>> violations = validator.validate(registerDto);
    System.out.println("got here");
    System.out.println(violations);
    for (ConstraintViolation cv : violations) {
      System.out.println(cv);
    }
    if (!violations.isEmpty()) {
      System.out.println("validation failed");
      StringBuilder sb = new StringBuilder();
      for (ConstraintViolation<RegisterRequestDTO> violation : violations) {
        sb.append(violation.getMessage()).append("\n");
      }
      throw new IllegalArgumentException("Validation error: " + sb.toString());
    } else {
      System.out.println("validation passed");
      Parent parent =
          Parent.builder()
              .firstName(registerDto.getFirstName())
              .surname(registerDto.getSurname())
              //                    .password(passwordEncoder.encode(registerDto.getPassword()))
              .password(registerDto.getPassword())
              .email(registerDto.getEmail())
              .build();

      return Optional.of(parentRepository.save(parent));
    }
  }
}
