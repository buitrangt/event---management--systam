package org.aibles.eventmanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResetPasswordRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String  resetPasswordKey;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmPassword;

}
