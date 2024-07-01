package org.aibles.eventmanagementsystem.dto.response;

import lombok.Getter;

@Getter
public class ActiveAccountResponse {
    private final String code;
    private final long timestamp;
    private final Object data;
    private final Object error;

    public ActiveAccountResponse(String code, long timestamp, Object data, Object error) {
        this.code = code;
        this.timestamp = timestamp;
        this.data = data;
        this.error = error;
    }
}
