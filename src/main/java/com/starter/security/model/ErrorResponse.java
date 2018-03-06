package com.starter.security.model;

import com.starter.security.ErrorCode;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Value
public class ErrorResponse {

    private final HttpStatus status;
    private final String message;
    private final ErrorCode errorCode;
    private final Date timestamp;

    private ErrorResponse(final String message, final ErrorCode errorCode, HttpStatus status) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.timestamp = new Date();
    }

    public static ErrorResponse of(final String message, final ErrorCode errorCode, HttpStatus status) {
        return new ErrorResponse(message, errorCode, status);
    }

}
