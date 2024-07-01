package org.aibles.eventmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.eventmanagementsystem.constant.CacheConstant;
import org.aibles.eventmanagementsystem.constant.OtpFailedUnlockedTime;
import org.aibles.eventmanagementsystem.constant.ResponseCode;
import org.aibles.eventmanagementsystem.dto.request.VerifyResetPasswordOtpRequest;
import org.aibles.eventmanagementsystem.dto.response.BaseResponse;
import org.aibles.eventmanagementsystem.dto.response.VerifyResetPasswordOtpResponse;
import org.aibles.eventmanagementsystem.exception.exception.EmailNotFoundException;
import org.aibles.eventmanagementsystem.exception.exception.OTPInvalidException;
import org.aibles.eventmanagementsystem.repository.UserRepository;
import org.aibles.eventmanagementsystem.service.RedisService;
import org.aibles.eventmanagementsystem.service.VerifyResetPasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Base64;
import java.util.Optional;


@Service
@Transactional

public class VerifyResetPasswordSeviceImpl implements VerifyResetPasswordService {
    private static final Logger log = LoggerFactory.getLogger(VerifyResetPasswordSeviceImpl.class);
    private final UserRepository userRepository;
    private final RedisService redisService;

    public VerifyResetPasswordSeviceImpl(UserRepository userRepository, RedisService redisService) {
        this.userRepository = userRepository;
        this.redisService = redisService;
    }

    @Override
    public BaseResponse<VerifyResetPasswordOtpResponse> verifyResetPassword(VerifyResetPasswordOtpRequest request) {
        log.info("Starting OTP verification process for email: {}", request.getEmail());
        if (!userRepository.existsByEmail(request.getEmail())) {
            log.error("Email not found in the system: {}", request.getEmail());
            throw new EmailNotFoundException("Email not found: " + request.getEmail());
        }
        String resetPassOtpKey = request.getEmail() + CacheConstant.RESET_PASSWORD_OTP_KEY;
        Optional<Object> storedOtpOptional = redisService.get(resetPassOtpKey);
        String storedOtp = (String) storedOtpOptional.orElse(null);

        if (storedOtp == null || !storedOtp.equals(request.getOtp())) {
            log.error("Invalid OTP provided for email: {}. Provided OTP: {}", request.getEmail(), request.getOtp());
            handleFailedAttempt(request.getEmail());
            throw new OTPInvalidException("Invalid OTP: " + request.getOtp());
        }
        log.info("OTP verified successfully for email: {}", request.getEmail());
        redisService.delete(CacheConstant.OTP_FAILED_UNLOCK_TIME_KEY, request.getEmail());
        redisService.delete(CacheConstant.FAILED_OTP_ATTEMPT_KEY, request.getEmail());

        String resetPasswordKey = generateResetPasswordKey(request.getEmail());
        redisService.save(CacheConstant.RESET_PASSWORD_KEY, request.getEmail(), resetPasswordKey);

        log.info("Reset password key generated and saved for email: {}", request.getEmail());
        VerifyResetPasswordOtpResponse response = new VerifyResetPasswordOtpResponse(request.getEmail(), resetPasswordKey);

        return new BaseResponse<>(ResponseCode.SUCCESS.getValue(), System.currentTimeMillis(), response);
    }

    private void handleFailedAttempt(String email) {
        log.info("Handling failed OTP attempt for email: {}", email);
        Integer attempts = redisService.getOrDefault(CacheConstant.FAILED_OTP_ATTEMPT_KEY, email, 0);
        attempts++;
        redisService.save(CacheConstant.FAILED_OTP_ATTEMPT_KEY, email, attempts);

        if (attempts == OtpFailedUnlockedTime.FIVE.getAttempts()) {
            redisService.save(CacheConstant.OTP_FAILED_UNLOCK_TIME_KEY,
                    email, Instant.now().getEpochSecond() + OtpFailedUnlockedTime.FIVE.getCooldownTime());
            log.info("Account locked for 5 failed OTP attempts for email: {}", email);
        }
    }

    private String generateResetPasswordKey(String email) {
        return Base64.getEncoder().encodeToString((email + System.currentTimeMillis()).getBytes());
    }
}
