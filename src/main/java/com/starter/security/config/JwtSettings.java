package com.starter.security.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtSettings {

    private static final Integer TOKEN_EXPIRATION_TIME = 15;

    private static final String TOKEN_ISSUER = "http://calewiz.com";

    private static final String TOKEN_SIGNING_KEY = "xsru389er394skSGHd3asdsad";

    private static final Integer REFRESH_TOKEN_EXPIRY_TIME = 60;

    public static Integer getTokenExpirationTime() {
        return TOKEN_EXPIRATION_TIME;
    }

    public static String getTokenIssuer() {
        return TOKEN_ISSUER;
    }

    public static String getTokenSigningKey() {
        return TOKEN_SIGNING_KEY;
    }

    public static Integer getRefreshTokenExpiryTime() {
        return REFRESH_TOKEN_EXPIRY_TIME;
    }

}
