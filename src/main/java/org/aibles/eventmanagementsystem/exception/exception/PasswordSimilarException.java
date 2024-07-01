package org.aibles.eventmanagementsystem.exception.exception;

public class PasswordSimilarException extends BadRequestException {
    public PasswordSimilarException(String message) {
        super(message);
    }
}
