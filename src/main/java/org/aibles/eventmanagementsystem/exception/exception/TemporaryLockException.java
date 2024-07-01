package org.aibles.eventmanagementsystem.exception.exception;

public class TemporaryLockException extends RuntimeException {
    public TemporaryLockException(String message) {
        super(message);
    }
}