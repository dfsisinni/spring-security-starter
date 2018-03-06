package com.starter.security.auth.ajax;

import com.starter.security.user.CustomUserDetailsService;
import com.starter.security.user.UserContext;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {

    private final BCryptPasswordEncoder encoder;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public AjaxAuthenticationProvider(BCryptPasswordEncoder encoder, CustomUserDetailsService userDetailsService) {
        this.encoder = encoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");
        val username = (String) authentication.getPrincipal();
        val password = (String) authentication.getCredentials();

        val userDetails = userDetailsService.loadUserByUsername(username);
        if (!userDetails.isEnabled()) {
            throw new BadCredentialsException("User is not active!");
        }

        if (!encoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        }


        val userContext = UserContext.create(userDetails.getUsername(), userDetails.getAuthorities());
        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
