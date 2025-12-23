package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest authRequest) {
        // Return dummy response for testing
        AuthResponse response = new AuthResponse();
        response.setEmail(authRequest.getEmail());
        response.setMessage("User registration disabled for testing");
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    @Operation(summary = "Login user")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        AuthResponse response = new AuthResponse();
        response.setEmail(authRequest.getEmail());
        response.setMessage("Login successful (JWT not implemented)");
        
        return ResponseEntity.ok(response);
    }
}