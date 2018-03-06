package com.starter.models.v1;

import com.starter.security.model.token.JwtToken;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class V1TokenResponse {

    private String token;

    private String refreshToken;

    public V1TokenResponse(JwtToken token, JwtToken refreshToken) {
        this.token = token.getToken();
        this.refreshToken = refreshToken.getToken();
    }

}
