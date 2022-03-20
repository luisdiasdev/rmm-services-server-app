package com.luisdias.rmmservice.modules.customer;

import com.luisdias.rmmservice.modules.customer.api.request.CreateCustomerRequest;
import com.luisdias.rmmservice.modules.device.api.request.CreateUpdateDeviceRequest;
import com.luisdias.rmmservice.modules.service.api.request.AddServiceToCustomerRequest;
import com.luisdias.rmmservice.modules.shared.enums.OperatingSystem;
import com.luisdias.rmmservice.support.AbstractIT;
import com.luisdias.rmmservice.support.data.AvailableServices;
import com.luisdias.rmmservice.support.data.Customers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.luisdias.rmmservice.support.JwtTokenExtractor.extractJwtTokenFromResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerIntegrationTest extends AbstractIT {

    @Test
    @DisplayName("should not allow to calculate bill for another customer")
    void calculateBillDifferentCustomer() {
        var jwtToken = extractJwtTokenFromResponse(
                loginClient.doLogin(Customers.Existing.USERNAME, Customers.Existing.PASSWORD));

        // Calculate Monthly Bill for another customer
        var anotherCustomerId = Customers.Existing.ID + 25;
        ResponseEntity<?> response = customerClient.calculateMonthlyBill(jwtToken, anotherCustomerId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("new customer should be able to add services, devices and calculate monthly bill")
    void createNewCustomer() {
        // Create Customer
        var createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setUsername(Customers.New.USERNAME);
        createCustomerRequest.setPassword(Customers.New.PASSWORD);
        var createCustomerResponse = customerClient.createCustomer(createCustomerRequest);
        assertThat(createCustomerResponse.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);
        assertThat(createCustomerResponse.getBody()).isNotNull();
        var customerId = createCustomerResponse.getBody();

        // Extract Jwt Token / Login
        var jwtToken = extractJwtTokenFromResponse(
                loginClient.doLogin(Customers.New.USERNAME, Customers.New.PASSWORD));

        // Add CustomerService
        var addServiceRequest = new AddServiceToCustomerRequest();
        addServiceRequest.setCustomerId(customerId);
        addServiceRequest.setServiceId(AvailableServices.ANTIVIRUS_ID);
        var createServiceResponse = serviceClient.addCustomerService(jwtToken, addServiceRequest);
        assertThat(createServiceResponse.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);
        assertThat(createServiceResponse.getBody()).isNotNull();
        var customerServiceId = createServiceResponse.getBody();

        // Add Device
        CreateUpdateDeviceRequest createDeviceRequest = new CreateUpdateDeviceRequest();
        createDeviceRequest.setSystemName("bigsur-01");
        createDeviceRequest.setOperatingSystem(OperatingSystem.MAC);
        var createDeviceResponse = deviceClient.addDevice(jwtToken, createDeviceRequest);
        assertThat(createDeviceResponse.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);
        assertThat(createDeviceResponse.getBody()).isNotNull();
        var deviceId = createDeviceResponse.getBody().getId();

        // Calculate Monthly Bill (1 device = $4 + (Antivirus for Mac = $7) = $11 (1100 cents)
        var calculateResponse = customerClient.calculateMonthlyBill(jwtToken, customerId);
        assertThat(calculateResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(calculateResponse.getBody())
                .isNotNull()
                .extracting("costInCents")
                .isEqualTo(1100L);
    }
}
