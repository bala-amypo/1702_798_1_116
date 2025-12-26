package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.entity.User;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "User authentication operations")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, 
                         JwtTokenProvider jwtTokenProvider,
                         UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public ResponseEntity<Map<String, String>> register(@RequestBody AuthRequest authRequest) {
        User user = userService.registerUser(authRequest.getEmail(), authRequest.getPassword());
        
        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail(), user.getRoles());
        
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("type", "Bearer");
        response.put("message", "User registered successfully");
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @Operation(summary = "User login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        
        User user = userService.getUserByEmail(authRequest.getEmail());
        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail(), user.getRoles());
        
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("type", "Bearer");
        
        return ResponseEntity.ok(response);
    }
}