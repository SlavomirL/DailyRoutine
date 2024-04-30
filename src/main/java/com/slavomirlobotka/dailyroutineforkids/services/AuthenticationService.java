package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.AuthenticationResponseDto;
import com.slavomirlobotka.dailyroutineforkids.dtos.LoginRequestDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterRequestDTO;
import com.slavomirlobotka.dailyroutineforkids.models.Parent;
import java.io.IOException;

public interface AuthenticationService {

  void registerNewParent(RegisterRequestDTO registerRequestDTO) throws Exception;

  String generateAndSaveActivationToken(Parent parent);

  String generateActivationCode(int length);

  boolean verifyUserCode(String code) throws Exception;

  AuthenticationResponseDto authenticate(LoginRequestDTO loginRequestDTO);
}
