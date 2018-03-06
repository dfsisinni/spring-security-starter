package com.starter.security.user;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public class UserContext {

    @Getter
    private final String username;

    @Getter
    private final Set<GrantedAuthority> authorities;

    private UserContext(String username, Set <GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }

    public static UserContext create(String username, Set <GrantedAuthority> authorities) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException(String.format("Username is blank: %s", username));
        }

        if (!Authorities.validAuthorities(authorities)) {
            throw new IllegalStateException("Invalid authorities!");
        }

        return new UserContext(username, authorities);
    }

}
