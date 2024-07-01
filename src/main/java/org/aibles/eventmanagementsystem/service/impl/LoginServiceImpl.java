package org.aibles.eventmanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aibles.eventmanagementsystem.constant.CacheConstant;
import org.aibles.eventmanagementsystem.constant.LoginAttempt;
import org.aibles.eventmanagementsystem.dto.request.LoginRequest;
import org.aibles.eventmanagementsystem.dto.response.LoginResponse;
import org.aibles.eventmanagementsystem.entity.Account;
import org.aibles.eventmanagementsystem.exception.ErrorResponse;
import org.aibles.eventmanagementsystem.exception.exception.BadRequestException;
import org.aibles.eventmanagementsystem.exception.exception.NotFoundException;
import org.aibles.eventmanagementsystem.repository.AccountRepository;
import org.aibles.eventmanagementsystem.repository.UserRepository;
import org.aibles.eventmanagementsystem.service.JWTService;
import org.aibles.eventmanagementsystem.service.LoginService;
import org.aibles.eventmanagementsystem.service.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final RedisService redisService;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Value("${expirationMs:}")
    private Long jwtExpirationMs;
    @Value("${refreshExpirationMs:}")
    private Long jwtRefreshExpirationMs;

    public LoginServiceImpl(AccountRepository accountRepository, UserRepository userRepository, RedisService redisService, JWTService jwtService, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.redisService = redisService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Object login(LoginRequest loginRequest) {
        log.info("Login request: {}", loginRequest);

        Account account = accountRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new NotFoundException("username: " + loginRequest.getUsername() + " not found"));

        if (account.isLockPermanent()) {
            log.info("Account is permanently locked: {}", loginRequest.getUsername());
            throw new BadRequestException("Account is permanently locked");
        }

        Long unlockTime = redisService.getOrDefault(CacheConstant.UNLOCK_TIME_KEY + account.getUsername(),
                account.getUsername(), null);

        if (unlockTime != null && Instant.now().getEpochSecond() < unlockTime) {
            log.info("Account is temporarily locked: {}", loginRequest.getUsername());
            throw new BadRequestException("Account is temporarily locked");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
            log.info("Password does not match for username: {}", loginRequest.getUsername());
            incrementFailedLoginAttempts(loginRequest.getUsername());
            throw new BadRequestException("Invalid password");
        }

        redisService.delete(loginRequest.getUsername());

        if (!account.isActivated()) {
            log.info("Account needs to be activated: {}", loginRequest.getUsername());
            throw new BadRequestException("Account needs to be activated");
        }

        String accessToken = jwtService.generateAccessToken(account);
        String refreshToken = jwtService.generateRefreshToken(account);

        String accessTokenHashkey = CacheConstant.ACCESS_TOKEN_HASH_KEY;
        String refreshTokenHashkey = CacheConstant.REFRESH_TOKEN_HASH_KEY;

        redisService.saveWithExpire(account.getUsername(), accessTokenHashkey, accessToken, jwtExpirationMs);
        redisService.saveWithExpire(account.getUsername(),refreshTokenHashkey, refreshToken, jwtRefreshExpirationMs);


        redisService.delete(CacheConstant.FAILED_PASSWORD_ATTEMPT_KEY, loginRequest.getUsername());

        log.info("Login successful for username: {}", loginRequest.getUsername());
        return new LoginResponse(accessToken, refreshToken, jwtExpirationMs, jwtRefreshExpirationMs);
    }

    private void incrementFailedLoginAttempts(String key) {
        Integer attempts = redisService.getOrDefault(CacheConstant.FAILED_PASSWORD_ATTEMPT_KEY, key, 0);
        log.info("Current failed login attempts for {}: {}", key, attempts);

        redisService.save(CacheConstant.FAILED_PASSWORD_ATTEMPT_KEY, key, attempts + 1);

        if (attempts == LoginAttempt.FIVE.getValue()) {
            redisService.save(CacheConstant.UNLOCK_TIME_KEY + key,
                    key, Instant.now().getEpochSecond() + CacheConstant.FIVE_MINUTES);
            log.info("Account temporarily locked due to 5 failed login attempts: {}", key);
            throw new BadRequestException("Account temporarily locked due to 5 failed login attempts");
        }
        if (attempts == LoginAttempt.TEN.getValue()) {
            redisService.save(CacheConstant.UNLOCK_TIME_KEY + key,
                    key, Instant.now().getEpochSecond() + CacheConstant.TEN_MINUTES);
            log.info("Account temporarily locked due to 10 failed login attempts: {}", key);
            throw new BadRequestException("Account temporarily locked due to 10 failed login attempts");
        }
        if (attempts >= LoginAttempt.FIFTEEN.getValue()) {
            Account account = accountRepository.findByUsername(key)
                    .orElseThrow(() -> new NotFoundException("username: " + key + " not found"));
            redisService.delete(CacheConstant.UNLOCK_TIME_KEY + key, key);
            account.setLockPermanent(true);
            accountRepository.save(account);
            log.info("Account permanently locked due to 15 failed login attempts: {}", key);
            throw new BadRequestException("Account permanently locked due to 15 failed login attempts");
        }
    }
}
