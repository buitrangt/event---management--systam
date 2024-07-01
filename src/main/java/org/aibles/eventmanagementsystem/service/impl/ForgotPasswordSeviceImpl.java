package org.aibles.eventmanagementsystem.service.impl;

import org.aibles.eventmanagementsystem.constant.CacheConstant;
import org.aibles.eventmanagementsystem.constant.ResponseCode;
import org.aibles.eventmanagementsystem.dto.request.ForgotPasswordRequest;
import org.aibles.eventmanagementsystem.dto.response.BaseResponse;
import org.aibles.eventmanagementsystem.exception.exception.AccountLockedException;
import org.aibles.eventmanagementsystem.exception.exception.EmailDeliveryException;
import org.aibles.eventmanagementsystem.exception.exception.EmailNotFoundException;
import org.aibles.eventmanagementsystem.repository.UserRepository;
import org.aibles.eventmanagementsystem.service.EmailService;
import org.aibles.eventmanagementsystem.service.ForgotPasswordSevice;
import org.aibles.eventmanagementsystem.service.RedisService;
import org.aibles.eventmanagementsystem.util.OTPGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static org.aibles.eventmanagementsystem.constant.CacheConstant.OTP_TTL_MINUTES;


@Service
@Transactional
public class ForgotPasswordSeviceImpl implements ForgotPasswordSevice {

    private static final Logger log = LoggerFactory.getLogger(ForgotPasswordSeviceImpl.class);

    private final UserRepository userRepository;

    private final RedisService redisService;
    private final EmailService emailService;

    public ForgotPasswordSeviceImpl(UserRepository userRepository, RedisService redisService, EmailService emailService) {
        this.userRepository = userRepository;
        this.redisService = redisService;
        this.emailService = emailService;
    }

    @Override
    public BaseResponse<Void> forgotPassword(ForgotPasswordRequest request) {
        log.info("Processing forgot password for email: {}", request.getEmail());
        if (!userRepository.existsByEmail(request.getEmail())) {
            log.error("Email not found in the system: {}", request.getEmail());
            throw new EmailNotFoundException("Email not found: " + request.getEmail());
        }
        long lockedTime = redisService.getOrDefault(CacheConstant.OTP_FAILED_UNLOCK_TIME_KEY, request.getEmail(), 0L);
        if (Instant.now().getEpochSecond() < lockedTime) {
            log.error("Account is locked until: {}", lockedTime);
            throw new AccountLockedException("Account is locked until: " + lockedTime);
        }
        redisService.delete(CacheConstant.OTP_FAILED_UNLOCK_TIME_KEY, request.getEmail());

        String otp = OTPGenerator.generateOtp();
        redisService.save(request.getEmail() + CacheConstant.RESET_PASSWORD_OTP_KEY, otp,
                OTP_TTL_MINUTES, TimeUnit.MINUTES);
        final String OTP_PASSWORD_RESET_SUBJECT = "Your OTP for Password Reset";
        final String OTP_TEMPLATE = "OTP-template";
        var param = new HashMap<String, Object>();
        param.put("otp", otp);
        param.put("otp_life", String.valueOf(OTP_TTL_MINUTES));
        try {
            emailService.send(OTP_PASSWORD_RESET_SUBJECT, request.getEmail(), OTP_TEMPLATE, param);
        } catch (Exception e) {
            log.error("Failed to send OTP email to: {}", request.getEmail(), e);
            throw new EmailDeliveryException("Failed to send OTP email to: " + request.getEmail());
        }
        log.info("OTP for password reset sent successfully to email: {}", request.getEmail());
        return new BaseResponse<>(ResponseCode.SUCCESS.getValue(), System.currentTimeMillis(), null);
    }
}
