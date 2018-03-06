package com.starter.security.model.token;

import com.starter.security.model.Scopes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.val;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Value
@AllArgsConstructor
public class RefreshToken implements JwtToken {

    private Jws<Claims> claims;

    public static Optional<RefreshToken> create(RawAccessJwtToken token, String signingKey) {
        val claims = token.parseClaims(signingKey);
        val scopes = claims.getBody().get("scopes", List.class);

        if (CollectionUtils.isEmpty(scopes) || scopes.stream().noneMatch(scope -> Scopes.REFRESH_TOKEN.authority().equals(scope))) {
            return Optional.empty();
        }

        return Optional.of(new RefreshToken(claims));
    }

    @Override
    public String getToken() {
        return null;
    }

    public String getJti() {
        return this.claims.getBody().getId();
    }

    public String getSubject() {
        return this.claims.getBody().getSubject();
    }

}
