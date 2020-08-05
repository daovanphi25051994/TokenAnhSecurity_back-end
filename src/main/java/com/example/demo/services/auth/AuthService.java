package com.example.demo.services.auth;

import com.example.demo.dtos.JWTResponse;
import com.example.demo.dtos.LoginRequest;
import com.example.demo.exceptions.RegisterException;
import com.example.demo.exceptions.UsernameExistedException;
import com.example.demo.models.User;

public interface AuthService {
    JWTResponse authenticate(LoginRequest loginRequest);
    User register(User user) throws UsernameExistedException, RegisterException;
}
