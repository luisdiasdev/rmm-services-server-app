package com.luisdias.rmmservice.modules.service;

import com.luisdias.rmmservice.modules.service.api.request.AddServiceToCustomerRequest;
import com.luisdias.rmmservice.support.AbstractIntegrationTest;
import com.luisdias.rmmservice.support.data.AvailableServices;
import com.luisdias.rmmservice.support.data.Customers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static com.luisdias.rmmservice.support.JwtTokenExtractor.extractJwtTokenFromResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class CustomerServiceIntegrationTest extends AbstractIntegrationTest {

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
        var jwtToken = extractJwtTokenFromResponse(
                loginClient.doLogin(Customers.Existing.USERNAME, Customers.Existing.PASSWORD));

        // GET /
        var allServicesResponse = serviceClient.getAllServices(jwtToken);
        assertThat(allServicesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        var allServices = allServicesResponse.getBody();
        assertThat(allServices).isNotEmpty();

        // POST /
        var addRequest = new AddServiceToCustomerRequest();
        addRequest.setCustomerId(Customers.Existing.ID);
        addRequest.setServiceId(AvailableServices.PSA_ID);
        ResponseEntity<Long> addResponse = serviceClient.addCustomerService(jwtToken, addRequest);
        assertThat(addResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        var customerServiceId = addResponse.getBody();
        assertThat(customerServiceId).isNotNull();

        // DELETE /{id}
        var deleteResponse = serviceClient.deleteServiceById(jwtToken, customerServiceId);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
