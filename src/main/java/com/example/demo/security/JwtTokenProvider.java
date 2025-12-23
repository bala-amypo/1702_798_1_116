package com.example.demo.security;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    // Mock implementation for testing
    public String generateToken(String username) {
        return "mock-jwt-token-" + username;
    }
    
    public boolean validateToken(String token) {
        return token != null && token.startsWith("mock-jwt-token-");
    }
    
    public String getUsernameFromToken(String token) {
        if (token == null || !token.startsWith("mock-jwt-token-")) {
            return null;
        }
        return token.substring("mock-jwt-token-".length());
    }
}