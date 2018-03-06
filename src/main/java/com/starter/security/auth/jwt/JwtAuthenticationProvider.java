package com.starter.security.auth.jwt;

import com.starter.security.auth.JwtAuthenticationToken;
import com.starter.security.config.JwtSettings;
import com.starter.security.model.token.RawAccessJwtToken;
import com.starter.security.user.UserContext;
import lombok.val;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toSet;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        val rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();

        val jwsClaims = rawAccessToken.parseClaims(JwtSettings.getTokenSigningKey());
        val subject = jwsClaims.getBody().getSubject();
        val scopes = (List<String>) jwsClaims.getBody().get("scopes", List.class);
        val authorities = scopes.stream()
                .map(authority -> (GrantedAuthority) new SimpleGrantedAuthority(authority))
                .collect(toSet());
        val context = UserContext.create(subject, authorities);

        return new JwtAuthenticationToken(context, context.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
