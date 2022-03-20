package com.luisdias.rmmservice.modules.shared.service;

import com.luisdias.rmmservice.infra.security.JwtUserToken;
import com.luisdias.rmmservice.modules.shared.dto.AuthenticatedCustomer;
import com.luisdias.rmmservice.modules.shared.exception.UnauthorizedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    public static Optional<JwtUserToken> getAuthentication() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof JwtUserToken
                ? Optional.of((JwtUserToken) authentication)
                : Optional.empty();
    }

    public Optional<AuthenticatedCustomer> getAuthenticatedCustomer() {
        return getAuthentication()
                .map(AuthenticatedCustomer::new);
    }

    public Long getAuthenticatedCustomerId() {
        return getAuthenticatedCustomer()
                .map(AuthenticatedCustomer::getCustomerId)
                .orElseThrow(UnauthorizedException::new);
    }
}
