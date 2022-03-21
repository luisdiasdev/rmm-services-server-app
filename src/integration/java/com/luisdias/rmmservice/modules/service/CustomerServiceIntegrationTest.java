package com.luisdias.rmmservice.modules.service;

import com.github.javafaker.Faker;
import com.luisdias.rmmservice.modules.customer.api.request.CreateCustomerRequest;
import com.luisdias.rmmservice.modules.service.api.request.AddServiceToCustomerRequest;
import com.luisdias.rmmservice.support.AbstractIntegrationTest;
import com.luisdias.rmmservice.support.data.AvailableServices;
import com.luisdias.rmmservice.support.data.Customers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Locale;

import static com.luisdias.rmmservice.support.JwtTokenExtractor.extractJwtTokenFromResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class CustomerServiceIntegrationTest extends AbstractIntegrationTest {

    @DisplayName("should return 404 when deleting invalid service")
    @Test
    void shouldReturnNotFound() {
        var jwtToken = extractJwtTokenFromResponse(
                loginClient.doLogin(Customers.Existing.USERNAME, Customers.Existing.PASSWORD));

        var nonExistentServiceId = 500L;
        assertThatExceptionOfType(HttpClientErrorException.class)
                .isThrownBy(() -> serviceClient.deleteServiceById(jwtToken, nonExistentServiceId))
                .extracting("statusCode")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @DisplayName("should not allow customer to add multiple times the same service")
    @Test
    void shouldValidateCustomerWithDuplicateService() {
        var jwtToken = extractJwtTokenFromResponse(
                loginClient.doLogin(Customers.Existing.USERNAME, Customers.Existing.PASSWORD));

        // POST /
        var addRequest = new AddServiceToCustomerRequest();
        addRequest.setCustomerId(Customers.Existing.ID);
        addRequest.setServiceId(AvailableServices.ANTIVIRUS_ID);
        assertThatExceptionOfType(HttpClientErrorException.class)
                .isThrownBy(() -> serviceClient.addCustomerService(jwtToken, addRequest))
                .extracting("statusCode")
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @DisplayName("should get all, create and delete customer services")
    @Test
    void shouldDoAllOperationsOnService() {
        var faker = new Faker(Locale.forLanguageTag("en-US"));
        var username = faker.name().username();
        var password = faker.name().firstName();
        var customerRequest = new CreateCustomerRequest();
        customerRequest.setPassword(password);
        customerRequest.setUsername(username);
        var newCustomerResponse = customerClient.createCustomer(customerRequest);
        assertThat(newCustomerResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        var newCustomerId = newCustomerResponse.getBody();

        var jwtToken = extractJwtTokenFromResponse(
                loginClient.doLogin(username, password));

        // POST /
        var addRequest = new AddServiceToCustomerRequest();
        addRequest.setCustomerId(newCustomerId);
        addRequest.setServiceId(AvailableServices.PSA_ID);
        ResponseEntity<Long> addResponse = serviceClient.addCustomerService(jwtToken, addRequest);
        assertThat(addResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        var customerServiceId = addResponse.getBody();
        assertThat(customerServiceId).isNotNull();

        // GET /
        var allServicesResponse = serviceClient.getAllServices(jwtToken);
        assertThat(allServicesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        var allServices = allServicesResponse.getBody();
        assertThat(allServices).isNotEmpty();

        // DELETE /{id}
        var deleteResponse = serviceClient.deleteServiceById(jwtToken, AvailableServices.PSA_ID);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
