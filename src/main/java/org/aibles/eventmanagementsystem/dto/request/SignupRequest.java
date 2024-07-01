package org.aibles.eventmanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.aibles.eventmanagementsystem.validation.ValidateEmail;
import org.aibles.eventmanagementsystem.validation.ValidatePassword;

@Data
public class SignupRequest {

    private String email;

    @NotBlank
    private String username;


    private String password;

    private String confirmPassword;

}