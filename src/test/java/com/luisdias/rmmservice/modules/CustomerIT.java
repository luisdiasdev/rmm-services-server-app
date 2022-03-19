package com.luisdias.rmmservice.modules;

import com.luisdias.rmmservice.modules.shared.api.request.LoginRequest;
import com.luisdias.rmmservice.support.AbstractIT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerIT extends AbstractIT {

    @Test
    @DisplayName("should return forbidden when trying to login with bad credentials")
    public void loginForbidden() {
        var requestBody = new LoginRequest();
        requestBody.setUsername("luisdias");
        requestBody.setPassword("somepassword");
        ResponseEntity<?> response = testRestTemplate.exchange(String.format("%s/v1/login", localURL()), HttpMethod.POST,
                new HttpEntity<>(requestBody), Object.class);

        assertThat(response.getStatusCodeValue())
                .isEqualTo(403);
    }

    @Test
    @DisplayName("should return ok when trying to login with valid credentials")
    public void loginOk() {
        var requestBody = new LoginRequest();
        requestBody.setUsername("luisdias");
        requestBody.setPassword("NQqm6JHWpzrzk5wxsTV84PUZp");
        ResponseEntity<?> response = testRestTemplate.exchange(String.format("%s/v1/login", localURL()), HttpMethod.POST,
                new HttpEntity<>(requestBody), Object.class);

        assertThat(response.getStatusCodeValue())
                .isEqualTo(200);
        assertThat(response.getHeaders().getOrEmpty("Authorization"))
                .isNotEmpty();
        assertThat(response.getHeaders().get("Authorization").get(0))
                .startsWith("Bearer ");
    }
}
