package org.aibles.eventmanagementsystem.service;

import org.aibles.eventmanagementsystem.dto.request.SendActiveAccountOtpRequest;

public interface SendActiveAccountOtp {
    void sendActiveAccountOtp(SendActiveAccountOtpRequest request);
}
