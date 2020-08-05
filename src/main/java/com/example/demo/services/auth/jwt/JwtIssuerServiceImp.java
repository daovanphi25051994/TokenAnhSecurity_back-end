package com.example.demo.services.auth.jwt;

import com.example.demo.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Component
@Slf4j
public class JwtIssuerServiceImp implements JwtIssuerService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expireTime}")
    private Long expireTime;

    @Override
    public String generateToken(UserDetails userDetails) {
        Date loginTime = new Date();
        Date expiryTime = new Date();
        expiryTime.setTime(loginTime.getTime() + expireTime);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(loginTime)
                .setExpiration(expiryTime)
                .signWith(SignatureAlgorithm.HS512, secretKey)
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
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
