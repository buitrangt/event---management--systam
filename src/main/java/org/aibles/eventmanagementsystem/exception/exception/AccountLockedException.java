package org.aibles.eventmanagementsystem.exception.exception;

import org.aibles.eventmanagementsystem.exception.BaseException;

public class AccountLockedException extends BadRequestException {
    public AccountLockedException (String message) {
        super(message);
    }
}
