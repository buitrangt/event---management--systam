package org.aibles.eventmanagementsystem.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VerifyResetPasswordOtpResponse {

    private String email;
    private String resetPasswordKey;

}
