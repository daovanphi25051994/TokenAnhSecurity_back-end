package com.example.demo.services.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtIssuerServiceImp implements JwtIssuerService {

    private String secretKey = "phi";
    private Long expireTime = Long.valueOf(86400000);

    @Override
    public String generateToken(UserDetails userDetails) {
        Date loginTime = new Date();
        Date expiryTime = new Date();
        expiryTime.setTime(loginTime.getTime() + expireTime);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(loginTime)
                .setExpiration(expiryTime)
                .signWith(SignatureAlgorithm.ES256, secretKey)
                .compact();
    }

    @Override
    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            System.err.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.err.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.err.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.err.println("JWT claims string is empty");
        }
        return false;
    }
}
