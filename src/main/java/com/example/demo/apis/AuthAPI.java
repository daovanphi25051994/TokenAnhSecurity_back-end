package com.example.demo.apis;

import com.example.demo.dtos.JWTResponse;
import com.example.demo.dtos.LoginRequest;
import com.example.demo.exceptions.CannotLoginException;
import com.example.demo.exceptions.RegisterException;
import com.example.demo.exceptions.UsernameExistedException;
import com.example.demo.exceptions.UsernameOrPasswordInvalidException;
import com.example.demo.models.User;
import com.example.demo.services.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthAPI {

    private final AuthService authService;

    @Autowired
    public AuthAPI(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public JWTResponse doLogin(@RequestBody LoginRequest loginRequest) throws CannotLoginException {
        try {
            JWTResponse jwtResponse = authService.authenticate(loginRequest);
            return jwtResponse;
        }catch (Exception ex){
            throw new CannotLoginException();
        }

    }

    @PostMapping("/register")
    public User doRegister(@RequestBody User user) throws RegisterException, UsernameExistedException {
        User newUser = authService.register(user);
        return newUser;
    }

    @ExceptionHandler(RegisterException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String registerException() {
        return "{\"error\":\"cannot Register!\"}";
    }

    @ExceptionHandler(UsernameExistedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String usernameExistedException() {
        return "{\"error\":\"Username Existed!\"}";
    }

    @ExceptionHandler(CannotLoginException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String LoginException() {
        return "{\"error\":\"Cannot Login!\"}";
    }
}
