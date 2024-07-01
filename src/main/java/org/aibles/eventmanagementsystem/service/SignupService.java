package org.aibles.eventmanagementsystem.service;

import org.aibles.eventmanagementsystem.dto.request.SignupRequest;
import org.aibles.eventmanagementsystem.dto.response.SignupResponse;


public interface SignupService {

    SignupResponse signup(SignupRequest request);

}
