package org.aibles.eventmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.eventmanagementsystem.constant.ResponseCode;
import org.aibles.eventmanagementsystem.constant.Role;
import org.aibles.eventmanagementsystem.dto.request.SignupRequest;
import org.aibles.eventmanagementsystem.dto.response.SignupResponse;
import org.aibles.eventmanagementsystem.entity.Account;
import org.aibles.eventmanagementsystem.entity.AccountRole;
import org.aibles.eventmanagementsystem.entity.AccountUser;
import org.aibles.eventmanagementsystem.entity.User;
import org.aibles.eventmanagementsystem.exception.exception.BadRequestException;
import org.aibles.eventmanagementsystem.exception.exception.ConflictException;
import org.aibles.eventmanagementsystem.repository.*;
import org.aibles.eventmanagementsystem.service.SignupService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SignupServiceImpl implements SignupService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;
    private final AccountUserRepository accountUserRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public SignupResponse signup(SignupRequest request) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException(ResponseCode.PASSWORDS_DO_NOT_MATCH.getValue());
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException(ResponseCode.EMAIL_ALREADY_EXISTS.getValue());
        }

        if (accountRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException(ResponseCode.USERNAME_ALREADY_EXISTS.getValue());
        }

        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setId(user.getId());
        user.setEmail(request.getEmail());
        userRepository.save(user);

        Account account = new Account();
        account.setId(account.getId());
        account.setUsername(request.getUsername());
        account.setPassword(encryptedPassword);
        account.setActivated(false);
        account.setLockPermanent(false);
        accountRepository.save(account);

        AccountRole accountRole = new AccountRole();
        accountRole.setId(accountRole.getId());
        accountRole.setAccountId(account.getId());
        accountRole.setRoleId(Role.USER_ROLE_ID);
        accountRoleRepository.save(accountRole);

        AccountUser accountUser = new AccountUser();
        accountUser.setId(accountUser.getId());
        accountUser.setAccountId(account.getId());
        accountUser.setUserId(user.getId());
        accountUserRepository.save(accountUser);

        String successMessage = String.valueOf(ResponseCode.SUCCESS);
        return new SignupResponse(request.getEmail(), request.getUsername(), successMessage);
    }
}
