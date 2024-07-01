package org.aibles.eventmanagementsystem.service;

import org.aibles.eventmanagementsystem.dto.request.LoginRequest;

public interface LoginService {
    Object login(LoginRequest loginRequest);
}
