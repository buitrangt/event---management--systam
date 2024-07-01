package org.aibles.eventmanagementsystem.service.impl;

import org.aibles.eventmanagementsystem.constant.CacheConstant;
import org.aibles.eventmanagementsystem.constant.ResponseCode;
import org.aibles.eventmanagementsystem.dto.request.ResetPasswordRequest;
import org.aibles.eventmanagementsystem.dto.response.BaseResponse;
import org.aibles.eventmanagementsystem.exception.exception.EmailNotFoundException;
import org.aibles.eventmanagementsystem.exception.exception.InvalidResetPasswordKeyException;
import org.aibles.eventmanagementsystem.exception.exception.PasswordConfirmNotMatchException;
import org.aibles.eventmanagementsystem.exception.exception.PasswordSimilarException;
import org.aibles.eventmanagementsystem.repository.AccountRepository;
import org.aibles.eventmanagementsystem.repository.UserRepository;
import org.aibles.eventmanagementsystem.service.RedisService;
import org.aibles.eventmanagementsystem.service.ResetPasswordService;
import org.aibles.eventmanagementsystem.util.EncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private final Logger log = LoggerFactory.getLogger(ResetPasswordServiceImpl.class);

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final RedisService redisService;

    public ResetPasswordServiceImpl(UserRepository userRepository, AccountRepository accountRepository, RedisService redisService) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.redisService = redisService;
    }

    @Override
    public BaseResponse<Void> resetPassword(ResetPasswordRequest request) {
        log.info("Received reset password request for email: {}", request.getEmail());

        validatePasswordMatch(request);
        validateEmailExists(request.getEmail());
        validatePasswordDifference(request);

        redisService.get(CacheConstant.RESET_PASSWORD_KEY, request.getEmail())
                .ifPresentOrElse(
                        resetPasswordKey -> validateResetPasswordKey(request, (String) resetPasswordKey),
                        () -> {
                            log.error("No reset password key found for email: {}", request.getEmail());
                            throw new InvalidResetPasswordKeyException("Invalid reset password key.");
                        }
                );

        updatePassword(request);

        log.info("Password updated successfully for email: {}", request.getEmail());
        return new BaseResponse<>(ResponseCode.SUCCESS.getValue(), System.currentTimeMillis(), null);
    }

    private void validatePasswordMatch(ResetPasswordRequest request) {
        if (!Objects.equals(request.getNewPassword(), request.getConfirmPassword())) {
            log.warn("New password does not match confirm password for email: {}", request.getEmail());
            throw new PasswordConfirmNotMatchException("New password and confirm password do not match.");
        }
    }

    private void validateEmailExists(String email) {
        if (!userRepository.existsByEmail(email)) {
            log.warn("Email not found in the system: {}", email);
            throw new EmailNotFoundException("Email not found: " + email);
        }
    }

    private void validatePasswordDifference(ResetPasswordRequest request) {
        String oldPassword = accountRepository.findPasswordByEmail(request.getEmail());
        if (EncryptUtil.getPasswordEncoder().matches(request.getNewPassword(), oldPassword)) {
            log.warn("New password is the same as the old password for email: {}", request.getEmail());
            throw new PasswordSimilarException("New password must be different from the old password.");
        }
    }

    private void validateResetPasswordKey(ResetPasswordRequest request, String resetPasswordKey) {
        if (!Objects.equals(resetPasswordKey, request.getResetPasswordKey())) {
            log.error("Invalid reset password key for email: {}. Provided key: {}", request.getEmail(), request.getResetPasswordKey());
            throw new InvalidResetPasswordKeyException("Invalid reset password key.");
        }
    }

    private void updatePassword(ResetPasswordRequest request) {
        String encryptedNewPassword = EncryptUtil.getPasswordEncoder().encode(request.getNewPassword());
        accountRepository.updatePasswordByEmail(request.getEmail(), encryptedNewPassword);
    }
}
