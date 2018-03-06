package com.starter.security.user;

import com.starter.models.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public class CustomUserPrincipal implements UserDetails {

    private User user;

    CustomUserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return Authorities.generateGrantedAuthorities(user.getId(), user.getEmail());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
