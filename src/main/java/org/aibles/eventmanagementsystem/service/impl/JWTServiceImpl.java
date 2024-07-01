package org.aibles.eventmanagementsystem.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.aibles.eventmanagementsystem.entity.Account;
import org.aibles.eventmanagementsystem.exception.exception.BadRequestException;
import org.aibles.eventmanagementsystem.service.JWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
public class JWTServiceImpl implements JWTService {
    @Value("${jwtSecret}")
    private String jwtSecret;
    @Value("${expirationMs}")
    private Long jwtExpirationMs;
    @Value("${refreshExpirationMs}")
    private Long jwtRefreshExpirationMs;

    private Key key() {
        byte[] decodedKey = Base64.getDecoder().decode(jwtSecret);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
    }

    @Override
    public String generateAccessToken(Account account) {
        return Jwts.builder()
                .setSubject(account.getUsername())
                .claim("id", account.getId())
                .claim("username", account.getUsername())
                .claim("isActivated", account.isActivated())
                .claim("isLockPermanent", account.isLockPermanent())
                .claim("createdBy", account.getCreatedBy())
                .claim("createdAt", account.getCreatedAt())
                .claim("lastUpdatedBy", account.getLastUpdatedBy())
                .claim("lastUpdatedAt", account.getLastUpdatedAt())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, key())
                .compact();
    }

    @Override
    public String generateRefreshToken(Account account) {
        return Jwts.builder()
                .setSubject(account.getUsername())
                .claim("id", account.getId())
                .claim("username", account.getUsername())
                .claim("isActivated", account.isActivated())
                .claim("isLockPermanent", account.isLockPermanent())
                .claim("createdBy", account.getCreatedBy())
                .claim("createdAt", account.getCreatedAt())
                .claim("lastUpdatedBy", account.getLastUpdatedBy())
                .claim("lastUpdatedAt", account.getLastUpdatedAt())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtRefreshExpirationMs))
                .signWith(SignatureAlgorithm.HS256, key())
                .compact();
    }

    @Override
    public String parseToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key())
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new BadRequestException("Token is invalid");
        }
    }
}