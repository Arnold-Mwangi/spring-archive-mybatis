package com.kirigwi.springarchive.authorisation.userManagement.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Log4j2
public class JWTService {


    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String getSecretKey() {
        return SECRET_KEY;
    }
    public String generateToken(String username, String role) {
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role)  // Add the role claim
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }


    public String extractUsername(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new AuthenticationCredentialsNotFoundException("Invalid or tampered token.");
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (JWTVerificationException e) {
            // If the token is invalid, handle it by logging and returning false
            log.error("Token validation failed: " + e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token);
            return jwt.getExpiresAt().before(new Date());
        } catch (JWTVerificationException e) {
            // Log the error or handle it as necessary
            log.error("Token expiration check failed: " + e.getMessage());
            return true;  // If verification fails, assume the token is expired
        }
    }

    public String extractRole(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token);
            return jwt.getClaim("role").asString();  // Get the single role from the token
        } catch (JWTVerificationException e) {
            log.error("Role extraction failed: " + e.getMessage());
            throw new AuthenticationCredentialsNotFoundException("Invalid or expired token.");
        }
    }

}
