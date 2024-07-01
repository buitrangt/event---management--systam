package org.aibles.eventmanagementsystem.facade.impl;

import lombok.RequiredArgsConstructor;
import org.aibles.eventmanagementsystem.dto.request.ActiveAccountRequest;
import org.aibles.eventmanagementsystem.dto.request.SignupRequest;
import org.aibles.eventmanagementsystem.dto.response.ActiveAccountResponse;
import org.aibles.eventmanagementsystem.dto.response.SignupResponse;
import org.aibles.eventmanagementsystem.facade.AccountFacade;
import org.aibles.eventmanagementsystem.service.ActiveAccountService;
import org.aibles.eventmanagementsystem.service.SignupService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountFacadeImpl implements AccountFacade {

    private final SignupService signupService;
    private final ActiveAccountService activeAccountService;

    @Override
    public SignupResponse signup(SignupRequest request) {
        return signupService.signup(request);
    }

    @Override
    public ActiveAccountResponse activateAccount(ActiveAccountRequest request) {
        return activeAccountService.activeAccount(request);
    }


}
