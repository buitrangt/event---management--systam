package org.aibles.eventmanagementsystem.service;

import org.aibles.eventmanagementsystem.dto.request.VerifyResetPasswordOtpRequest;
import org.aibles.eventmanagementsystem.dto.response.BaseResponse;
import org.aibles.eventmanagementsystem.dto.response.VerifyResetPasswordOtpResponse;

public interface VerifyResetPasswordService {
    BaseResponse<VerifyResetPasswordOtpResponse> verifyResetPassword(VerifyResetPasswordOtpRequest request);
}
