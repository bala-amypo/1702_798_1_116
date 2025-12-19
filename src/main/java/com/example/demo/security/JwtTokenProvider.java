package com.example.demo.security;

public class JwtTokenProvider {

    public String generateToken(String email) {
        return email + "-token";
    }

    public boolean validateToken(String token) {
        return token != null && token.endsWith("-token");
    }

    public String getEmailFromToken(String token) {
        return token.replace("-token", "");
    }
}
