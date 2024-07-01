package org.aibles.eventmanagementsystem.exception.exception;

public class InvalidResetPasswordKeyException extends BadRequestException{
    public InvalidResetPasswordKeyException(String message) {
        super(message);
    }
}
