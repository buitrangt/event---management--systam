package org.aibles.eventmanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendActiveAccountOtpRequest {
    @NotBlank
    private String username;
}
