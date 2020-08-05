package com.example.demo.services.user;

import com.example.demo.models.User;

import java.util.List;

public interface UserService {
    User getById(Long id);
    User getByUsername(String username);
    boolean isExisted(User user);
    User save(User user);
    List<User> getAllUsers();
}
