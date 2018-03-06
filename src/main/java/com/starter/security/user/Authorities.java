package com.starter.security.user;

import com.google.common.collect.ImmutableMap;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class Authorities {

    public static final String ID_KEY = "id";
    public static final String EMAIL_KEY = "email";

    public static Map<String, Object> getPropertiesFromGrantedAuthorities(Set<GrantedAuthority> authorities) {
        val filtered = authorities.stream().map(x -> x.getAuthority().split(":")).collect(toList());

        val userId = filtered.stream().filter(x -> x.length == 2 && x[0].equals(ID_KEY) && StringUtils.isNumeric(x[1]))
                .map(x -> Long.valueOf(x[1]))
                .findFirst().orElseThrow(() -> new IllegalStateException("Invalid user id!"));

        val email = filtered.stream().filter(x -> x.length == 2 && x[0].equals(EMAIL_KEY))
                .map(x -> String.valueOf(x[1]))
                .findFirst().orElseThrow(() -> new IllegalStateException("Invalid email!"));

        return new ImmutableMap.Builder<String, Object>()
                .put(ID_KEY, userId)
                .put(EMAIL_KEY, email)
                .build();
    }

    public static Set<GrantedAuthority> generateGrantedAuthorities(Long userId, String email) {
        val map = new HashSet<GrantedAuthority>();
        map.add(new SimpleGrantedAuthority(ID_KEY + ":" + userId));
        map.add(new SimpleGrantedAuthority(EMAIL_KEY + ":" + email));
        return map;
    }

    public static boolean validAuthorities(Set <GrantedAuthority> authorities) {
        if (authorities.size() != 2) {
            return false;
        }

        val filtered = authorities.stream().map(x -> x.getAuthority().split(":")).collect(toList());

        val userId = filtered.stream().filter(x -> x.length == 2 && x[0].equals(ID_KEY) && StringUtils.isNumeric(x[1])).count() == 1;
        val email = filtered.stream().filter(x -> x.length == 2 && x[0].equals(EMAIL_KEY)).count() == 1;

        return userId && email;
    }

}
