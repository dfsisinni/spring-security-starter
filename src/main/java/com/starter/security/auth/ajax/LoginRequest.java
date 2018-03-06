package com.starter.security.auth.ajax;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class LoginRequest {

    private final String username;
    private final String password;

    public LoginRequest() {
        this.username = null;
        this.password = null;
    }

    public LoginRequest(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

}
