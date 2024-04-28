package com.slavomirlobotka.dailyroutineforkids.email;

import java.io.IOException;

public interface EmailService {

  String sendEmail(String emailTo, String firstName, String digitToken) throws IOException;

  String buildEmailContent(String username, String email, String digitToken);

  String buildVerificationLink(String email);
}
