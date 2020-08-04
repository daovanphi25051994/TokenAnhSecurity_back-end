package com.example.demo.services.auth.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtIssuerService {
    String generateToken(UserDetails userDetails);
    String getUsernameFromJwt(String token);
    boolean validateToken(String token);
}
