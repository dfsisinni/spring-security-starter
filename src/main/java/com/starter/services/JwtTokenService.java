package com.starter.services;

import com.starter.models.v1.V1TokenResponse;
import com.starter.security.model.token.JwtTokenFactory;
import com.starter.security.user.Authorities;
import com.starter.security.user.UserContext;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

    private final JwtTokenFactory tokenFactory;

    @Autowired
    public JwtTokenService(JwtTokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }

    public V1TokenResponse generateTokensForUser(Long userId, String email) {
        val grantedAuthorities = Authorities.generateGrantedAuthorities(userId, email);
        val userContext = UserContext.create(email, grantedAuthorities);

        val accessToken = tokenFactory.createAccessJwtToken(userContext);
        val refreshToken = tokenFactory.createRefreshToken(userContext);

        return new V1TokenResponse(accessToken, refreshToken);
    }
}
