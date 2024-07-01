package org.aibles.eventmanagementsystem.facade;

import org.aibles.eventmanagementsystem.dto.request.ActiveAccountRequest;
import org.aibles.eventmanagementsystem.dto.request.SignupRequest;
import org.aibles.eventmanagementsystem.dto.response.ActiveAccountResponse;
import org.aibles.eventmanagementsystem.dto.response.SignupResponse;

public interface AccountFacade {
    SignupResponse signup(SignupRequest request);
    ActiveAccountResponse activateAccount(ActiveAccountRequest request);

}
