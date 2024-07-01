package org.aibles.eventmanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aibles.eventmanagementsystem.validation.ValidateEmail;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResendOtpRequest {

    @NotBlank
    @ValidateEmail
    private String email;
}
