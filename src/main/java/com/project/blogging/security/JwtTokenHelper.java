package com.project.blogging.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

// step two
@Component
public class JwtTokenHelper {

//    private final String SECRET_KEY = "jwtTokenKey"; // Replace with your secret key
	private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

	// Extract username from token
	public String extractUsername(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// Extract all claims from token
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}

	// Check if the token is expired
	private boolean isTokenExpired(String token) {
		return getAllClaimsFromToken(token).getExpiration().before(new Date());
	}

	// Generate token for user
	public String generateToken(UserDetails UserDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, UserDetails.getUsername());
	}

	// Create the JWT token
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		Date now = new Date(System.currentTimeMillis());
		Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);

		JwtBuilder builder = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(now)
				.setExpiration(expirationDate).signWith(SignatureAlgorithm.HS256, secretKey);

		return builder.compact();
	}

	// Validate token
	public boolean validateToken(String token, UserDetails UserDetails) {
		final String username = extractUsername(token);
		return (username.equals(UserDetails.getUsername()) && !isTokenExpired(token));
	}

}


