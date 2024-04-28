package com.slavomirlobotka.dailyroutineforkids.email;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailConfirmationTokenRepository
    extends JpaRepository<EmailConfirmationToken, Long> {
  EmailConfirmationToken findByToken(String code);
}
