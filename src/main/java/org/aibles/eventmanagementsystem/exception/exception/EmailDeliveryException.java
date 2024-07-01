package org.aibles.eventmanagementsystem.exception.exception;

public class EmailDeliveryException extends InternalErrorException {
    public EmailDeliveryException (String message) {
        super(message);
    }
}
