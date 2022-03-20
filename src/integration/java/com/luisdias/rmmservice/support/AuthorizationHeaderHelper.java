package com.luisdias.rmmservice.support;

import org.springframework.http.HttpHeaders;

import java.util.List;

public class AuthorizationHeaderHelper {

    public static HttpHeaders getHeaders(String jwtToken) {
        var headers = new HttpHeaders();
        headers.put(HttpHeaders.AUTHORIZATION, List.of("Bearer " + jwtToken));
        return headers;
    }
}
