package com.project.blogging.utills;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import com.project.blogging.exception.InvalidJwtException;

@Component
public class JwtUtil {
	
	String secretKey = System.getenv("JWT_SECRET");

//    private String secretKey = "your-secret-key"; // Replace with your actual secret key

    public Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException ex) {
            throw new InvalidJwtException("Invalid JWT: Signature verification failed!");
        }
    }
}

