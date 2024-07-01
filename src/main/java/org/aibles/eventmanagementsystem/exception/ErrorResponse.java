package org.aibles.eventmanagementsystem.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse<T> {

    private String code;
    private long timestamps;
    private T error;

}
