package com.starter.security;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {

    GLOBAL(2),
    AUTHENTICATION(10),
    JWT_TOKEN_EXPIRED(11);

    private final int errorCode;

    @JsonValue
    public int getErrorCode() {
        return this.errorCode;
    }
}