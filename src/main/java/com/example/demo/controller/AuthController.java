package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final UserService userService;
    
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest authRequest) {
        User user = userService.registerUser(authRequest.getEmail(), authRequest.getPassword());
        
        AuthResponse response = new AuthResponse();
        response.setEmail(user.getEmail());
        response.setMessage("User registered successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    @Operation(summary = "Login user")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        // For now, just validate user exists
        User user = userService.getUserByEmail(authRequest.getEmail());
        
        AuthResponse response = new AuthResponse();
        response.setEmail(user.getEmail());
        response.setMessage("Login successful (JWT not implemented)");
        
        return ResponseEntity.ok(response);
    }
}