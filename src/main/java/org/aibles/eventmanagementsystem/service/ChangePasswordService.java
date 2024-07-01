package org.aibles.eventmanagementsystem.service;

import org.aibles.eventmanagementsystem.entity.Account;

public interface ChangePasswordService {
    void changePassword(String accountId, String oldPassword, String newPassword, String confirmPassword);

}
