package com.luisdias.rmmservice.support;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtTokenExtractor {

    public static String extractJwtTokenFromResponse(ResponseEntity<?> response) {
        var authHeader = response.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
        assertThat(authHeader).startsWith("Bearer ");
        return authHeader.split(" ")[1].trim();
    }
}
