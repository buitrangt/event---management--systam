package org.aibles.eventmanagementsystem.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.aibles.eventmanagementsystem.constant.CacheConstant;
import org.aibles.eventmanagementsystem.service.AuthTokenService;
import org.aibles.eventmanagementsystem.service.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    private final String accessTokenJwtSecret;
    private final String refreshTokenJwtSecret;
    private final RedisService redisService;

    public AuthTokenServiceImpl(
            @Value("${jwtSecret}") String accessTokenJwtSecret,
            @Value("${jwtSecret}") String refreshTokenJwtSecret, RedisService redisService) {
        this.accessTokenJwtSecret = accessTokenJwtSecret;
        this.refreshTokenJwtSecret = refreshTokenJwtSecret;
        this.redisService = redisService;
    }

    @Override
    @Transactional
    public String getSubjectFromAccessToken(String accessToken) {
        log.info("(getSubjectFromAccessToken)accessToken: {}", accessToken);
        return getClaim(accessToken, Claims::getSubject, accessTokenJwtSecret);
    }

    @Override
    @Transactional
    public boolean validateAccessToken(String accessToken, String username) {
        log.debug("(validateAccessToken)accessToken: {}, userId: {}", accessToken, username);
        String accessTokenHashkey = CacheConstant.ACCESS_TOKEN_HASH_KEY;
        if (redisService.findToken(username, accessTokenHashkey) == null ||
                !getSubjectFromAccessToken(accessToken).equals(username)) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public String getSubjectFromRefreshToken(String refreshToken) {
        log.info("(getSubjectFromRefreshToken)refreshToken: {}", refreshToken);
        return getClaim(refreshToken, Claims::getSubject, refreshTokenJwtSecret);

    }

    @Override
    @Transactional
    public boolean validateRefreshToken(String refreshToken, String username) {
        log.debug("(validateRefreshToken)refreshToken: {}, userId: {}", refreshToken, username);
        String refreshTokenHashkey = CacheConstant.REFRESH_TOKEN_HASH_KEY;
        if (redisService.findToken(username, refreshTokenHashkey) == null ||
                !getSubjectFromAccessToken(refreshToken).equals(username)) {
            return false;
        }
        return true;
    }

    private Claims getClaims(String token, String secretKey) {
        log.info("(getClaims)token: {} secretKey: {}", token, secretKey);
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolve, String secretKey) {
        log.info("(getClaim) token: {}, claimResolve: {}, secretKey: {}", token, claimsResolve, secretKey);
        return claimsResolve.apply(getClaims(token, secretKey));
    }

}