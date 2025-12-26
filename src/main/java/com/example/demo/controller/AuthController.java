package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "User authentication operations")
public class AuthController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest authRequest) {
        // Mock authentication - replace with actual authentication logic
        String token = jwtTokenProvider.generateToken(1L, authRequest.getEmail(), Set.of("USER"));
        
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("type", "Bearer");
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    @Operation(summary = "Validate token", description = "Validate JWT token")
    public ResponseEntity<Map<String, Boolean>> validateToken(@RequestParam String token) {
        boolean isValid = jwtTokenProvider.validateToken(token);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("valid", isValid);
        
        return ResponseEntity.ok(response);
    }
}