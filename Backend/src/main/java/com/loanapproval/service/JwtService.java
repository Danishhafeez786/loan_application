package com.loanapproval.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Generate signing key
     */
    private SecretKey getKey() {

        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    /**
     * Generate JWT token
     */
    public String generateToken(
            String email,
            String role
    ) {

        Date now = new Date();

        Date expiryDate =
                new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(
                        getKey(),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    /**
     * Extract email
     */
    public String extractEmail(String token) {

        return extractAllClaims(token)
                .getSubject();
    }

    /**
     * Extract role
     */
    public String extractRole(String token) {

        return extractAllClaims(token)
                .get("role", String.class);
    }

    /**
     * Validate token
     */
    public boolean validateToken(String token) {

        try {

            Claims claims =
                    extractAllClaims(token);

            return !isTokenExpired(claims);

        } catch (Exception e) {

            return false;
        }
    }

    /**
     * Check expiry
     */
    private boolean isTokenExpired(
            Claims claims
    ) {

        return claims.getExpiration()
                .before(new Date());
    }

    /**
     * Extract all claims
     */
    private Claims extractAllClaims(
            String token
    ) {

        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
}