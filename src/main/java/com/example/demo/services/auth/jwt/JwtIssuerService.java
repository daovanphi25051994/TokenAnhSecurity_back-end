package com.example.demo.services.auth.jwt;

import com.example.demo.models.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtIssuerService {
    String generateToken(UserDetails UserDetails);
    String getUsernameFromJwt(String token);
    boolean validateToken(String token);
}
