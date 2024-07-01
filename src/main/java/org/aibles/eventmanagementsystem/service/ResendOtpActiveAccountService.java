package org.aibles.eventmanagementsystem.service;

import org.aibles.eventmanagementsystem.dto.request.LoginRequest;
import org.aibles.eventmanagementsystem.dto.request.ResendOtpRequest;
import org.aibles.eventmanagementsystem.dto.response.BaseResponse;
import org.aibles.eventmanagementsystem.dto.response.LoginResponse;

public interface ResendOtpActiveAccountService {
    BaseResponse<Void>  resendOtp(ResendOtpRequest resendOtpRequest);
}
