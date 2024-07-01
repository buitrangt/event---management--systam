package org.aibles.eventmanagementsystem.controller;

import jakarta.validation.Valid;
import org.aibles.eventmanagementsystem.constant.ResponseCode;
import org.aibles.eventmanagementsystem.dto.request.*;
import org.aibles.eventmanagementsystem.dto.response.ActiveAccountResponse;
import org.aibles.eventmanagementsystem.dto.response.BaseResponse;
import org.aibles.eventmanagementsystem.dto.response.LoginResponse;
import org.aibles.eventmanagementsystem.dto.response.SignupResponse;
import org.aibles.eventmanagementsystem.dto.response.VerifyResetPasswordOtpResponse;
import org.aibles.eventmanagementsystem.exception.ErrorResponse;
import org.aibles.eventmanagementsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    private SignupService accountService;
    @Autowired
    private ActiveAccountService activeAccountService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private ResendOtpActiveAccountService resendOtpActiveAccountService;
    @Autowired
    private ResetPasswordService resetPasswordService;
    @Autowired

    private SendActiveAccountOtp sendActiveAccountOtpService;
    @Autowired
    private ChangePasswordService changePasswordService;
    @Autowired
    private ForgotPasswordSevice forgotPasswordService;
    @Autowired
    private VerifyResetPasswordService verifyResetPasswordService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<SignupResponse>> signup(@RequestBody @Valid SignupRequest request) {
        SignupResponse response = accountService.signup(request);
        BaseResponse<SignupResponse> baseResponse = new BaseResponse<>(
                ResponseCode.SUCCESS.getValue(),
                System.currentTimeMillis(),
                response
        );
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/users/account:active-otp")
    public ResponseEntity<ActiveAccountResponse> activateAccount(@RequestBody @Valid ActiveAccountRequest request) {
        ActiveAccountResponse response = activeAccountService.activeAccount(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<?>> login(@RequestBody @Valid LoginRequest request) {
        Object response = loginService.login(request);

        if (response instanceof LoginResponse) {
            return ResponseEntity.ok(new BaseResponse<>(
                    ResponseCode.SUCCESS.getValue(),
                    System.currentTimeMillis(),
                    (LoginResponse) response
            ));
        } else if (response instanceof ErrorResponse) {
            ErrorResponse<?> errorResponse = (ErrorResponse<?>) response;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>(
                    ResponseCode.INVALID_REQUEST.getValue(),
                    System.currentTimeMillis(),
                    errorResponse.getError()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(
                    ResponseCode.UNEXPECTED_ERROR.getValue(),
                    System.currentTimeMillis(),
                    "Unexpected error"
            ));
        }
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<BaseResponse<Void>> resendOtp(@RequestBody @Valid ResendOtpRequest request) {
        BaseResponse<Void> baseResponse = resendOtpActiveAccountService.resendOtp(request);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }


    @PostMapping("/users/account:active")
    public ResponseEntity<BaseResponse<Void>> activeAccount(@RequestBody @Valid SendActiveAccountOtpRequest request) {
        sendActiveAccountOtpService.sendActiveAccountOtp(request);
        BaseResponse<Void> baseResponse = new BaseResponse<>(
                ResponseCode.SUCCESS.getValue(),
                System.currentTimeMillis(),
                null
        );
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<BaseResponse<Void>> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        changePasswordService.changePassword(request.getAccountId(), request.getOldPassword(), request.getNewPassword(), request.getConfirmPassword());
        BaseResponse<Void> baseResponse = new BaseResponse<>(
                ResponseCode.SUCCESS.getValue(),
                System.currentTimeMillis(),
                null
        );
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<BaseResponse<Void>> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        BaseResponse<Void> response = resetPasswordService.resetPassword(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<BaseResponse<Void>> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        BaseResponse<Void> response = forgotPasswordService.forgotPassword(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/verify-reset-password-otp")
    public ResponseEntity<BaseResponse<VerifyResetPasswordOtpResponse>> verifyResetPasswordOtp(@RequestBody @Valid VerifyResetPasswordOtpRequest request) {
        BaseResponse<VerifyResetPasswordOtpResponse> response = verifyResetPasswordService.verifyResetPassword(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
