package com.example.demo.apis;

import com.example.demo.dtos.JWTResponse;
import com.example.demo.dtos.LoginRequest;
import com.example.demo.exceptions.CannotLoginException;
import com.example.demo.exceptions.RegisterException;
import com.example.demo.exceptions.UsernameExistedException;
import com.example.demo.exceptions.UsernameOrPasswordInvalidException;
import com.example.demo.models.AppRole;
import com.example.demo.models.User;
import com.example.demo.services.auth.AuthService;
import com.example.demo.services.role.RoleService;
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

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthAPI {

    public static final Long ROLE_USER = 1L;

    private final AuthService authService;
    private final RoleService roleService;

    @Autowired
    public AuthAPI(AuthService authService, RoleService roleService) {
        this.authService = authService;
        this.roleService = roleService;
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
        AppRole role = roleService.getById(ROLE_USER);
        Set<AppRole> roles = new HashSet<>();
        roles.add(role);
        user.setAppRoles(roles);
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
