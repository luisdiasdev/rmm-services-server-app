package com.luisdias.rmmservice.modules.shared.dto;

import com.luisdias.rmmservice.infra.security.JwtUserToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticatedUser {

    private Long userId;
    private String username;
    private Collection<GrantedAuthority> roles;

    public AuthenticatedUser(JwtUserToken authentication) {
        this.username = authentication.getName();
        this.userId = authentication.getUserId();
        this.roles = authentication.getAuthorities();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Collection<GrantedAuthority> roles) {
        this.roles = roles;
    }
}