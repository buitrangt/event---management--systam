package org.aibles.eventmanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aibles.eventmanagementsystem.constant.CacheConstant;
import org.aibles.eventmanagementsystem.entity.Account;
import org.aibles.eventmanagementsystem.exception.exception.BadRequestException;
import org.aibles.eventmanagementsystem.exception.exception.NotFoundException;
import org.aibles.eventmanagementsystem.exception.exception.PermanentLockException;
import org.aibles.eventmanagementsystem.exception.exception.TemporaryLockException;
import org.aibles.eventmanagementsystem.repository.AccountRepository;
import org.aibles.eventmanagementsystem.service.ChangePasswordService;
import org.aibles.eventmanagementsystem.service.RedisService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.aibles.eventmanagementsystem.constant.LoginAttempt.*;

@Slf4j
@Service
@Transactional
public class ChangePasswordServiceImpl implements ChangePasswordService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    public ChangePasswordServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder, RedisService redisService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
    }

    @Override
    public void changePassword(String accountId, String oldPassword, String newPassword, String confirmPassword) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        validatePasswords(account, oldPassword, newPassword, confirmPassword);

        String encodedPassword = passwordEncoder.encode(newPassword);
        account.setPassword(encodedPassword);
        accountRepository.save(account);
    }

    private void validatePasswords(Account account, String oldPassword, String newPassword, String confirmPassword) {
        if (!passwordEncoder.matches(oldPassword, account.getPassword())) {
            log.info("Password does not match");
            incrementFailedPasswordAttempts(account.getUsername());
            throw new BadRequestException("Password does not match");
        }

        if (!newPassword.equals(confirmPassword)) {
            log.error("newPassword and confirmPassword do not match");
            throw new BadRequestException("newPassword and confirmPassword do not match");
        }

        if (passwordEncoder.matches(oldPassword, newPassword)) {
            log.error("Old password and new password would be the same");
            throw new BadRequestException("Old password and new password would be the same");
        }
    }

    private void incrementFailedPasswordAttempts(String username) {
        Integer attempts = redisService.getOrDefault(CacheConstant.FAILED_PASSWORD_ATTEMPT_KEY, username, 0);
        log.info("Current failed login attempts for {}: {}", username, attempts);

        redisService.save(CacheConstant.FAILED_PASSWORD_ATTEMPT_KEY, username, attempts + 1);

        if (attempts == FIVE.getValue()) {
            lockAccountTemporarily(username, FIVE.getValue() * 60L, "Account temporarily locked due to 5 failed password attempts: {}");
        } else if (attempts == TEN.getValue()) {
            lockAccountTemporarily(username, TEN.getValue() * 60L, "Account temporarily locked due to 10 failed password attempts: {}");
        } else if (attempts >= FIFTEEN.getValue()) {
            lockAccountPermanently(username);
        }
    }

    private void lockAccountTemporarily(String username, long duration, String logMessage) {
        redisService.save(CacheConstant.UNLOCK_TIME_KEY + username, username, Instant.now().getEpochSecond() + duration);
        log.info(logMessage, username);
        throw new TemporaryLockException("User lock temporary failure");
    }

    private void lockAccountPermanently(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Account not found"));
        redisService.delete(CacheConstant.UNLOCK_TIME_KEY + username, username);
        account.setLockPermanent(true);
        accountRepository.save(account);
        log.info("Account permanently locked due to 15 failed password attempts: {}", username);
        throw new PermanentLockException("User lock permanent failure");
    }
}
