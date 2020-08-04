package com.example.demo.services.user;

import com.example.demo.models.User;

public interface UserService {
    User getById(Long id);
    User getByUsername(String username);
}
