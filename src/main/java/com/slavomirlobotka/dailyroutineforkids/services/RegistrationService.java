package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterRequestDTO;
import com.slavomirlobotka.dailyroutineforkids.models.Parent;

import java.util.Optional;

public interface RegistrationService {

    Optional<Parent> registerNewParent(RegisterRequestDTO registerRequestDTO);

}