package com.smartvault.auth_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "GCZaNQT87SE2e5WpL2mwfVF70t8fkt5pHWkZwxbAnnQ"; // Replace with a strong secret key

    // ✅ Extract Debit Card Number (Subject) from JWT Token
    public String extractDebitCardNumber(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ✅ Extract Expiration Date from JWT
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // ✅ Generic Method to Extract Claims
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseToken(token);
        return claimsResolver.apply(claims);
    }

    // ✅ Parse JWT Token to Retrieve Claims
    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ Generate Token for a Debit Card Number
    public String generateToken(String debitCardNumber) {
        return Jwts.builder()
                .setSubject(debitCardNumber)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token valid for 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // ✅ Check if Token is Expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ✅ Validate Token
    public boolean validateToken(String token, String debitCardNumber) {
        return (extractDebitCardNumber(token).equals(debitCardNumber) && !isTokenExpired(token));
    }
}