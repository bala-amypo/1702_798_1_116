package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "User authentication operations")
public class AuthController {

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public ResponseEntity<Map<String, String>> register(@RequestBody AuthRequest authRequest) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registration endpoint - implementation pending");
        response.put("email", authRequest.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @Operation(summary = "User login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest authRequest) {
        Map<String, String> response = new HashMap<>();
        response.put("token", "sample-jwt-token");
        response.put("type", "Bearer");
        response.put("email", authRequest.getEmail());
        return ResponseEntity.ok(response);
    }
}