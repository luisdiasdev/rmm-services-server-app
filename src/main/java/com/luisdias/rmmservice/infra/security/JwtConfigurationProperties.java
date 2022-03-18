package com.luisdias.rmmservice.infra.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@Configuration
@ConfigurationProperties("app.security.jwt")
public class JwtConfigurationProperties {

    private static final String DEFAULT_TOKEN_TYPE = "JWT";
    private static final Duration DEFAULT_TOKEN_EXPIRATION = Duration.ofHours(1);

    @NotNull
    @NotBlank
    private String secret;

    @NotNull
    @NotBlank
    private String signingAlgorithm;

    @NotNull
    @NotBlank
    private String tokenType = DEFAULT_TOKEN_TYPE;

    @NotNull
    @NotBlank
    private String tokenIssuer;

    @NotNull
    @NotBlank
    private String tokenAudience;

    @NotNull
    private Duration tokenExpiration = DEFAULT_TOKEN_EXPIRATION;

    public JwtConfigurationProperties() {
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSigningAlgorithm() {
        return signingAlgorithm;
    }

    public void setSigningAlgorithm(String signingAlgorithm) {
        this.signingAlgorithm = signingAlgorithm;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenIssuer() {
        return tokenIssuer;
    }

    public void setTokenIssuer(String tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }

    public String getTokenAudience() {
        return tokenAudience;
    }

    public void setTokenAudience(String tokenAudience) {
        this.tokenAudience = tokenAudience;
    }

    public Duration getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(Duration tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }


}
