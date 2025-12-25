package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenProvider {
    private String jwtSecret;
    private Long jwtExpirationMs;

    public String generateToken(Long userId, String email, Set<String> roles) {
        Date expiryDate = new Date(System.currentTimeMillis() + jwtExpirationMs);
        String rolesString = String.join(",", roles);

        return Jwts.builder()
                .claim("userId", userId.toString())
                .claim("email", email)
                .claim("roles", rolesString)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }
}