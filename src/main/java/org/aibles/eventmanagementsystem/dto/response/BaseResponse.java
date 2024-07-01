package org.aibles.eventmanagementsystem.dto.response;

import lombok.Data;

@Data
public class BaseResponse<T> {

    private String code;
    private long timestamp;
    private T data;

    public BaseResponse(String code, long timestamp, T data) {
        this.code = code;
        this.timestamp = timestamp;
        this.data = data;
    }

}
