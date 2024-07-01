package org.aibles.eventmanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aibles.eventmanagementsystem.constant.ResponseCode;
import org.aibles.eventmanagementsystem.dto.request.ResendOtpRequest;
import org.aibles.eventmanagementsystem.dto.response.BaseResponse;
import org.aibles.eventmanagementsystem.exception.exception.BadRequestException;
import org.aibles.eventmanagementsystem.repository.AccountRepository;
import org.aibles.eventmanagementsystem.repository.UserRepository;
import org.aibles.eventmanagementsystem.service.EmailService;
import org.aibles.eventmanagementsystem.service.RedisService;
import org.aibles.eventmanagementsystem.service.ResendOtpActiveAccountService;
import org.aibles.eventmanagementsystem.util.OTPGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static org.aibles.eventmanagementsystem.constant.CacheConstant.OTP_ACTIVE_ACCOUNT_KEY;
import static org.aibles.eventmanagementsystem.constant.CacheConstant.OTP_TTL_MINUTES;

@Service
@Transactional
public class ResendOtpActiveAccountServiceImpl implements ResendOtpActiveAccountService {

    private static final Logger log = LoggerFactory.getLogger(ResendOtpActiveAccountServiceImpl.class);
    private final AccountRepository accountRepository;
    private final RedisService redisService;
    private final EmailService emailService;
    private final UserRepository userRepository;

    public ResendOtpActiveAccountServiceImpl(AccountRepository accountRepository, RedisService redisService, EmailService emailService, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.redisService = redisService;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @Override
    public BaseResponse<Void> resendOtp(ResendOtpRequest resendOtpRequest) {
        log.info("email: {}", resendOtpRequest.getEmail());
        if(!userRepository.existsByEmail(resendOtpRequest.getEmail())) {
            log.error("email: {}", resendOtpRequest.getEmail());
            throw new BadRequestException("Account not found with email: " + resendOtpRequest.getEmail());
        }
        String otp = OTPGenerator.generateOtp();

        var redisKey = resendOtpRequest.getEmail() + OTP_ACTIVE_ACCOUNT_KEY;
        redisService.save(redisKey, otp, OTP_TTL_MINUTES, TimeUnit.MINUTES);
        String subject = "Your OTP for account activation";

        var param = new HashMap<String, Object>();
        param.put("otp", otp);
        param.put("otp_life", String.valueOf(OTP_TTL_MINUTES));
        emailService.send(subject, resendOtpRequest.getEmail(), "OTP-template", param);
        return new BaseResponse<>(ResponseCode.SUCCESS.getValue(), System.currentTimeMillis(), null);
    }
}
