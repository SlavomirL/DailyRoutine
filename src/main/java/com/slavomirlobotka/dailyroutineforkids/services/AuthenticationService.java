package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.AuthenticationResponseDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.LoginRequestDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterRequestDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineForbidden;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineIO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineUnauthorized;
import com.slavomirlobotka.dailyroutineforkids.models.User;

public interface AuthenticationService {

  void registerNewParent(RegisterRequestDTO registerRequestDTO)
      throws DailyRoutineIO, DailyRoutineForbidden;

  String generateAndSaveActivationToken(User user);

  String generateActivationCode(int length);

  boolean verifyUserCode(String code, String email) throws DailyRoutineBadRequest;

  AuthenticationResponseDTO authenticate(LoginRequestDTO loginRequestDTO)
      throws DailyRoutineUnauthorized, DailyRoutineBadRequest;

  boolean isUserEnabled(String email);

  User getCurrentParent();

  boolean isEmailAlreadyUsed(String email);
}
