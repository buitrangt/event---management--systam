package org.aibles.eventmanagementsystem.exception.exception;

public class OTPInvalidException extends BadRequestException{
    public OTPInvalidException(String message) {
        super(message);
    }
}
