package com.slavomirlobotka.dailyroutineforkids.email;

import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineIO;

public interface EmailService {

  void sendEmail(String emailTo, String firstName, String digitToken) throws DailyRoutineIO;

  String buildEmailContent(String username, String email, String digitToken);

  String buildVerificationLink(String email);
}
