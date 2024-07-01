package org.aibles.eventmanagementsystem.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ForgotPasswordRequest {
    private String email;
}
