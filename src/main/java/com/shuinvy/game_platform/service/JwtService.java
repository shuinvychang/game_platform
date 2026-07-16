package com.shuinvy.game_platform.service;

import com.shuinvy.game_platform.security.CustomUserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

public class JwtService {

    private final SecretKey secretKey;
    private final int validSeconds;
    private final JwtParser jwtParser;

    public JwtService(String secretKeyStr, int validSeconds) {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyStr.getBytes());
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
        this.validSeconds = validSeconds;
    }

    public String createLoginAccessToken(CustomUserDetail user) {
        long expirationMillis = Instant.now()
                .plusSeconds(validSeconds)
                .getEpochSecond()
                * 1000;

        Claims claims = Jwts.claims()
                .issuedAt(new Date())
                .expiration(new Date(expirationMillis))
                .add("user_id", user.getId())
                .add("username", user.getUsername())
                .add("role_id", user.getRoleId())
                .build();

        return Jwts.builder()
                .claims(claims)
                .signWith(secretKey)
                .compact();
    }

    public Claims parseToken(String jwt) throws JwtException {
        return jwtParser.parseSignedClaims(jwt).getPayload();
    }
}
