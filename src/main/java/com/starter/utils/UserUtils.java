package com.starter.utils;

import com.starter.security.user.Authorities;
import com.starter.security.user.UserContext;
import lombok.Builder;
import lombok.Data;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;

@Data
@Builder
public class UserUtils {

    private Long userId;
    private String email;

    public static UserUtils getUserProperties() {
        val userContext = (UserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getUserPropertiesFromUserContext(userContext);
    }

    public static UserUtils getUserPropertiesFromUserContext(UserContext userContext) {
        val properties = Authorities.getPropertiesFromGrantedAuthorities(userContext.getAuthorities());

        return UserUtils.builder()
                .userId((Long) properties.get(Authorities.ID_KEY))
                .email(userContext.getUsername())
                .build();
    }
}
