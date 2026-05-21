package com.loanapproval.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET =
            "mysecretkeymysecretkeymysecretkey";

    private final long EXPIRATION =
            1000 * 60 * 60 * 24;

    private Key getKey() {

        return Keys.hmacShaKeyFor(
                SECRET.getBytes()
        );
    }

    public String generateToken(String email, String role) {

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractRole(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public boolean hasRole(String token, String requiredRole) {

        String role = extractRole(token);

        return role.equals(requiredRole);
    }

    public boolean validateToken(String token) {

        try {

            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }
}
