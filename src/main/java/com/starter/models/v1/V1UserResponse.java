package com.starter.models.v1;

import com.starter.models.entities.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class V1UserResponse {

    private Long id;

    private String email;

    public static V1UserResponse fromUser(User user) {
        return V1UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

}
