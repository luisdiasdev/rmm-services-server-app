package com.luisdias.rmmservice.infra.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtUserToken extends UsernamePasswordAuthenticationToken {

    private Long userId;

    public JwtUserToken(Object principal,
                        Collection<? extends GrantedAuthority> authorities,
                        Long userId) {
        super(principal, null, authorities);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
