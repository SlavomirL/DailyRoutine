package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterRequestDTO;
import com.slavomirlobotka.dailyroutineforkids.models.Parent;
import com.slavomirlobotka.dailyroutineforkids.repositories.ParentRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

  private final ParentRepository parentRepository;

  public Optional<Parent> registerNewParent(RegisterRequestDTO registerDto) {
    Parent parent =
        Parent.builder()
            .firstName(registerDto.getFirstName())
            .surname(registerDto.getSurname())
            .password(registerDto.getPassword())
            .email(registerDto.getEmail())
            .build();

    return Optional.of(parentRepository.save(parent));
  }
}
