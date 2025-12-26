package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    User registerUser(String email, String password);
    User getUserByEmail(String email);
    User getUserById(Long id);
    boolean existsByEmail(String email);
}