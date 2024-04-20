package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterRequestDTO;
import com.slavomirlobotka.dailyroutineforkids.models.Parent;
import com.slavomirlobotka.dailyroutineforkids.repositories.ParentRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.Set;

@Service
@Validated
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final ParentRepository parentRepository;
    private final Validator validator;

    public Optional<Parent> registerNewParent(RegisterRequestDTO registerDto) {
        Set<ConstraintViolation<RegisterRequestDTO>> violations = validator.validate(registerDto);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<RegisterRequestDTO> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new IllegalArgumentException("Validation error: " + sb.toString());
        } else {
            Parent parent = Parent.builder()
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
