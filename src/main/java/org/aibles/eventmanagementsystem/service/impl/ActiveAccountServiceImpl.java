package org.aibles.eventmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.eventmanagementsystem.constant.CacheConstant;
import org.aibles.eventmanagementsystem.dto.request.ActiveAccountRequest;
import org.aibles.eventmanagementsystem.dto.response.ActiveAccountResponse;
import org.aibles.eventmanagementsystem.entity.Account;
import org.aibles.eventmanagementsystem.exception.exception.AccountAlreadyActivatedException;
import org.aibles.eventmanagementsystem.exception.exception.BadRequestException;
import org.aibles.eventmanagementsystem.exception.exception.NotFoundException;
import org.aibles.eventmanagementsystem.repository.AccountRepository;
import org.aibles.eventmanagementsystem.service.ActiveAccountService;
import org.aibles.eventmanagementsystem.service.RedisService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ActiveAccountServiceImpl implements ActiveAccountService {

    private final AccountRepository accountRepository;
    private final RedisService redisService;

    @Override
    public ActiveAccountResponse activeAccount(ActiveAccountRequest request) {
        String username = request.getUsername();
        String otp = request.getOtp();

        log.info("Starting account activation for username: {}", username);

        if (username == null) {
            throw new BadRequestException("username is required");
        }
        if (otp == null) {
            throw new BadRequestException("otp is required");
        }

        Optional<Account> account = accountRepository.findByUsername(username);

        if (!account.isPresent()) {
            throw new NotFoundException("username was not registered");
        }

        if (account.get().isActivated()) {
            throw new AccountAlreadyActivatedException("Account activated");
        }

        String cachedOtp = redisService.findOtp(username, CacheConstant.OTP_HASH_KEY);
        log.info("OTP from Redis: {}, received OTP: {}", cachedOtp, otp);

        if (cachedOtp == null) {
            log.info("OTP expired");
            throw new BadRequestException("otp is expirated");
        }

        if (!cachedOtp.equals(otp)) {
            log.info("Invalid OTP");
            throw new BadRequestException("otp is invalid");
        }

        accountRepository.updateActivationStatus(account.get().getId(), true);
        log.info("Account activated for username: {}", username);
        redisService.clearActiveOtp(username);

        return new ActiveAccountResponse("success", System.currentTimeMillis(), null, null);
    }
}
