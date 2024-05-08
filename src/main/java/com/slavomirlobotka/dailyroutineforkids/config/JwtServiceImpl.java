package com.slavomirlobotka.dailyroutineforkids.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

    @Service
    public class JwtServiceImpl implements JwtService {

        @Value("${jwt.key}")
        private String DAILY_ROUTINE_SECRET_KEY;

        @Override
        public String extractUsername(String token) {
            return extractClaim(token, Claims::getSubject);
        }

        @Override
        public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);

        }

        @Override
        public String generateToken(Authentication authentication) {
            String username = authentication.getName();
            ZonedDateTime currentDate = ZonedDateTime.now();
            ZonedDateTime expireDate = currentDate.plusSeconds(60 * 60);
            List<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return Jwts.builder()
                    .claim("roles", roles)
                    .subject(username)
                    .issuedAt(Date.from(currentDate.toInstant()))
                    .expiration(Date.from(expireDate.toInstant()))
                    .signWith(getSignIngKey())
                    .compact();
        }

        @Override
        public boolean isTokenValid(String token, UserDetails userDetails) {
            String username = extractUsername(token);
            return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        }

        @Override
        public boolean isTokenExpired(String token) {
            return extractExpiration(token).before(new Date());
        }

        @Override
        public Date extractExpiration(String token) {
            return extractClaim(token, Claims::getExpiration);
        }

        @Override
        public Claims extractAllClaims(String token) {
            return Jwts
                    .parser()
                    .verifyWith(getSignIngKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }

        @Override
        public SecretKey getSignIngKey() {
            byte[] keyBytes = Decoders.BASE64.decode(DAILY_ROUTINE_SECRET_KEY);
            return Keys.hmacShaKeyFor(keyBytes);
        }
}
