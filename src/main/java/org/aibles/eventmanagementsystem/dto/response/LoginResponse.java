package org.aibles.eventmanagementsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiration;
    private Long refreshTokenExpiration;
}