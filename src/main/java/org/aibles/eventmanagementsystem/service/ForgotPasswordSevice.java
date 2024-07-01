package org.aibles.eventmanagementsystem.service;

import org.aibles.eventmanagementsystem.dto.request.ForgotPasswordRequest;
import org.aibles.eventmanagementsystem.dto.response.BaseResponse;

public interface ForgotPasswordSevice {
    BaseResponse<Void> forgotPassword(ForgotPasswordRequest request);
}
