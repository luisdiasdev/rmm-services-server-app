package com.luisdias.rmmservice.support.client;

import com.luisdias.rmmservice.modules.shared.api.request.LoginRequest;
import com.luisdias.rmmservice.support.LocalServerResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

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
    }
}
