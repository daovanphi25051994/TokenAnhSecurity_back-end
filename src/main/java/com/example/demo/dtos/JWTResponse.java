package com.example.demo.dtos;
import com.example.demo.models.User;

public class JWTResponse {
    private String token;
    private User user;

    public JWTResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public JWTResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
