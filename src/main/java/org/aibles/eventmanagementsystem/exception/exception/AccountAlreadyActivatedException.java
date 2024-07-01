package org.aibles.eventmanagementsystem.exception.exception;

public class AccountAlreadyActivatedException extends RuntimeException {
    public AccountAlreadyActivatedException(String message) {
        super(message);
    }
}
