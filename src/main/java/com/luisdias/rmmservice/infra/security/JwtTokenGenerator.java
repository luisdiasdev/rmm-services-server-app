package com.luisdias.rmmservice.infra.security;

import com.luisdias.rmmservice.modules.customer.entity.Customer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenGenerator {

    private final JwtConfigurationProperties jwtConfigurationProperties;

    public JwtTokenGenerator(JwtConfigurationProperties jwtConfigurationProperties) {
        this.jwtConfigurationProperties = jwtConfigurationProperties;
    }

    public String generateForCustomer(Customer user) {
        var roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .signWith(
                        Keys.hmacShaKeyFor(jwtConfigurationProperties.getSecret().getBytes()),
                        SignatureAlgorithm.forName(jwtConfigurationProperties.getSigningAlgorithm()))
                .setHeaderParam("typ", jwtConfigurationProperties.getTokenType())
                .setIssuer(jwtConfigurationProperties.getTokenIssuer())
                .setAudience(jwtConfigurationProperties.getTokenAudience())
                .setSubject(user.getUsername())
                .setExpiration(this.getExpirationDate())
                .claim("rol", roles)
                .claim("userId", user.getId())
                .compact();
    }

    private Date getExpirationDate() {
        return Date.from(
                LocalDateTime.now()
                        .plusSeconds(jwtConfigurationProperties.getTokenExpiration().toSeconds())
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
    }
}
