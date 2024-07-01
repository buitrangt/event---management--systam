package org.aibles.eventmanagementsystem.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VerifyResetPasswordOtpRequest {

    private String email;
    private String otp;
}
