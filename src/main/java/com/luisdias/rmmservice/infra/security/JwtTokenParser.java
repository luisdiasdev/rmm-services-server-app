package com.luisdias.rmmservice.infra.security;

import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtTokenParser {

    private final JwtConfigurationProperties jwtConfigurationProperties;

    public JwtTokenParser(JwtConfigurationProperties jwtConfigurationProperties) {
        this.jwtConfigurationProperties = jwtConfigurationProperties;
    }

    public Optional<JwtUserToken> parseJwtToken(String token) {
        var parsedToken = Jwts.parserBuilder()
                .setSigningKey(jwtConfigurationProperties.getSecret().getBytes())
                .build()
                .parseClaimsJws(token);

        var username = parsedToken.getBody().getSubject();

        if (StringUtils.isEmpty(username)) {
            return Optional.empty();
        }

        @SuppressWarnings("unchecked")
        var roles = ((List<String>) parsedToken.getBody().get("rol", List.class))
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        var userId = parsedToken.getBody().get("userId", Long.class);

        return Optional.of(new JwtUserToken(username, roles, userId));
    }
}
