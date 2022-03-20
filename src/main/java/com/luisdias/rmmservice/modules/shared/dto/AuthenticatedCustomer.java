package com.luisdias.rmmservice.modules.shared.dto;

import com.luisdias.rmmservice.infra.security.JwtUserToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticatedCustomer {

    private Long customerId;
    private String username;
    private Collection<GrantedAuthority> roles;

    public AuthenticatedCustomer(JwtUserToken authentication) {
        this.username = authentication.getName();
        this.customerId = authentication.getUserId();
        this.roles = authentication.getAuthorities();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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