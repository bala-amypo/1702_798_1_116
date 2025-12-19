package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(String email, String password) {

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setCreatedAt(LocalDateTime.now()); // ✅ FIXED
        user.setRoles(Set.of("USER"));

        userRepository.save(user);
    }
}
