package com.luisdias.rmmservice.modules.login;

import com.luisdias.rmmservice.support.AbstractIntegrationTest;
import com.luisdias.rmmservice.support.data.Customers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginIntegrationTest extends AbstractIntegrationTest {

    @Test
    @DisplayName("should return forbidden when trying to login with bad credentials")
    void loginForbidden() {
        ResponseEntity<?> response = loginClient.doLogin(Customers.Existing.USERNAME, "somepassword");

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("should return ok when trying to login with existing customer")
    void loginOk() {
        ResponseEntity<?> loginResponse = loginClient.doLogin(Customers.Existing.USERNAME, Customers.Existing.PASSWORD);

        assertThat(loginResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(loginResponse.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION))
                .isNotEmpty();
    }
}
