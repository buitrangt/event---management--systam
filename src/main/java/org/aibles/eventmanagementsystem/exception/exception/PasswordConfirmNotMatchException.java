package org.aibles.eventmanagementsystem.exception.exception;


import org.aibles.eventmanagementsystem.exception.BaseException;

public class PasswordConfirmNotMatchException extends ConflictException {

        public PasswordConfirmNotMatchException(String message) {
            super(message);
        }

}