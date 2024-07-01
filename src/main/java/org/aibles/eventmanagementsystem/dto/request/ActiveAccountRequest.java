package org.aibles.eventmanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.aibles.eventmanagementsystem.validation.ValidateEmail;

@Data
public class ActiveAccountRequest {

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "otp is required")
    @Size(min = 6, max = 6, message = "otp length must be 6")
    private String otp;

}