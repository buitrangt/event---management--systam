package org.aibles.eventmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.eventmanagementsystem.constant.CacheConstant;
import org.aibles.eventmanagementsystem.constant.OtpTimeToLive;
import org.aibles.eventmanagementsystem.dto.request.SendActiveAccountOtpRequest;
import org.aibles.eventmanagementsystem.entity.Account;
import org.aibles.eventmanagementsystem.exception.exception.NotFoundException;
import org.aibles.eventmanagementsystem.repository.AccountRepository;
import org.aibles.eventmanagementsystem.service.EmailService;
import org.aibles.eventmanagementsystem.service.RedisService;
import org.aibles.eventmanagementsystem.service.SendActiveAccountOtp;
import org.aibles.eventmanagementsystem.util.OTPGenerator;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SendActiveAccountOtpServiceImpl implements SendActiveAccountOtp {

    private final AccountRepository accountRepository;
    private final RedisService redisService;
    private final EmailService emailService;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void sendActiveAccountOtp(SendActiveAccountOtpRequest request) {
        String username = request.getUsername();

        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isEmpty()) {
            throw new NotFoundException(" Username " + username + " not found ");
        }

        Optional<String> emailOpt = accountRepository.findEmailByUsername(username);
        if (emailOpt.isPresent()) {
            String email = emailOpt.get();
            String otp = OTPGenerator.generateOtp();

            String hashKey = CacheConstant.OTP_HASH_KEY;
            Long otp_time = OtpTimeToLive.OTP_ACTIVATE_TTL.getValue();
            redisService.saveWithExpire(username, hashKey, otp, otp_time);
            log.info("Send account {} with otp {}", username, otp);

            String subject = "Your OTP for account activation";
            Map<String, Object> param = new HashMap<>();
            param.put("otp", otp);
            param.put("otp_life", String.valueOf(OtpTimeToLive.OTP_ACTIVATE_TTL.getValue()));
            emailService.send(subject, email, "OTP-template", param);
            log.info("Send OTP email to user {} with otp {} successfully", username, otp);
        }
    }
}
