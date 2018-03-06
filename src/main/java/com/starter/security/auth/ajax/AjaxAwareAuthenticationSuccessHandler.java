package com.starter.security.auth.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starter.security.user.UserContext;
import com.starter.services.JwtTokenService;
import com.starter.utils.UserUtils;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AjaxAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper mapper;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public AjaxAwareAuthenticationSuccessHandler(ObjectMapper mapper, JwtTokenService jwtTokenService) {
        this.mapper = mapper;
        this.jwtTokenService = jwtTokenService;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        val userContext = (UserContext) authentication.getPrincipal();
        val properties = UserUtils.getUserPropertiesFromUserContext(userContext);

        val tokenResponse = jwtTokenService.generateTokensForUser(properties.getUserId(),  properties.getEmail());

        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(httpServletResponse.getWriter(), tokenResponse);

        clearAuthenticationAttributes(httpServletRequest);
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        val session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}
