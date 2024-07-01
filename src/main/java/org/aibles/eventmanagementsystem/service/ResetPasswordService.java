package org.aibles.eventmanagementsystem.service;

import org.aibles.eventmanagementsystem.dto.request.ResetPasswordRequest;
import org.aibles.eventmanagementsystem.dto.response.BaseResponse;

public interface ResetPasswordService {
    BaseResponse<Void> resetPassword(ResetPasswordRequest request);
}
