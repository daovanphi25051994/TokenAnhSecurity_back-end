package com.example.demo.services.auth;

import com.example.demo.dtos.JWTResponse;
import com.example.demo.dtos.LoginRequest;
import com.example.demo.models.User;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {
    @Override
    public JWTResponse authenticate(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public User register(User user) {
        return null;
    }
}
