package com.slavomirlobotka.dailyroutineforkids.config;

import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
  String extractEmail(String token);

  <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

  String generateToken(Authentication authentication);

  boolean isTokenValid(String token, UserDetails userDetails);

  boolean isTokenExpired(String token);

  Date extractExpiration(String token);

  Claims extractAllClaims(String token);

  SecretKey getSignIngKey();
}
