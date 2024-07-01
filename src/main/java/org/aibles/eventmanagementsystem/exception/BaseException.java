package org.aibles.eventmanagementsystem.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {
    private String code;
    private Timestamp timestamp;
    private String message;

    public BaseException(String message) {
        super(message);
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}