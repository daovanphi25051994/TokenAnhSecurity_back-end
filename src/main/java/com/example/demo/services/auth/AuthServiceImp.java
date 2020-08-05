package com.example.demo.services.auth;

import com.example.demo.dtos.JWTResponse;
import com.example.demo.dtos.LoginRequest;
import com.example.demo.exceptions.RegisterException;
import com.example.demo.exceptions.UsernameExistedException;
import com.example.demo.exceptions.UsernameExistedOrPasswordInvalidException;
import com.example.demo.models.User;
import com.example.demo.services.auth.jwt.JwtIssuerService;
import com.example.demo.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Set;

@Service
public class AuthServiceImp implements AuthService {

    private  AuthenticationManager authenticationManager;

    private JwtIssuerService jwtIssuerService;
    private UserService userService;

    @Autowired
    public AuthServiceImp(AuthenticationManager authenticationManager, JwtIssuerService jwtIssuerService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtIssuerService = jwtIssuerService;
        this.userService = userService;
    }

    @Override
    public JWTResponse authenticate(LoginRequest loginRequest) {
        JWTResponse jwtResponse = new JWTResponse();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        if (authentication.isAuthenticated()) {
            User userVerified = (User) authentication.getPrincipal();
            String token = jwtIssuerService.generateToken(userVerified);
            jwtResponse.setToken(token);
            jwtResponse.setUser((User) userVerified);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        return jwtResponse;
    }

    @Override
    public User register(User user) throws RegisterException {
        User userRegistered;
        try {
            userRegistered = saveUserToDB(user);
            return userRegistered;
        } catch (UsernameExistedOrPasswordInvalidException e) {
            throw new RegisterException();
        }
    }

    private User saveUserToDB(User user) throws UsernameExistedOrPasswordInvalidException {
       boolean isExisted = userService.isExisted(user);
       if (!isExisted && user.getPassword().equals(user.getConfirmPassword())){
          return userService.save(user);
       }else {
           throw new UsernameExistedOrPasswordInvalidException();
       }
    }
}
