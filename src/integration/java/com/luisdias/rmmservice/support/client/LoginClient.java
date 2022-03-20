package com.luisdias.rmmservice.support.client;

import com.luisdias.rmmservice.modules.shared.api.request.LoginRequest;
import com.luisdias.rmmservice.support.LocalServerResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginClient {

    @Autowired
    private LocalServerResolver localServerResolver;
    @Autowired
    private TestRestTemplate testRestTemplate;

    public ResponseEntity<?> doLogin(String username, String password) {
        var requestBody = new LoginRequest();
        requestBody.setUsername(username);
        requestBody.setPassword(password);
        return testRestTemplate.exchange(String.format("%s/v1/login", localServerResolver.getLocalURL()), HttpMethod.POST,
                new HttpEntity<>(requestBody), Object.class);

//        assertThat(response.getStatusCode())
//                .isEqualTo(HttpStatus.OK);
//        assertThat(response.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION))
//                .isNotEmpty();
//        var authHeader = response.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
//        assertThat(authHeader).startsWith("Bearer ");
//        return authHeader.split(" ")[1].trim();
    }
}
