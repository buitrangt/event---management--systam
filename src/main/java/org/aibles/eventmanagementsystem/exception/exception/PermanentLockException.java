package org.aibles.eventmanagementsystem.exception.exception;

public class PermanentLockException extends RuntimeException {
    public PermanentLockException(String message) {
        super(message);
    }
}