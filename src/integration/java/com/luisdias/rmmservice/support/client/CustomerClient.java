package com.luisdias.rmmservice.support.client;

import com.luisdias.rmmservice.modules.customer.api.request.CreateCustomerRequest;
import com.luisdias.rmmservice.modules.customer.api.response.CustomerBillResponse;
import com.luisdias.rmmservice.support.AuthorizationHeaderHelper;
import com.luisdias.rmmservice.support.LocalServerResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class CustomerClient {

    private static final String CUSTOMERS_API = "/v1/customers";

    @Autowired
    private LocalServerResolver localServerResolver;
    @Autowired
    private TestRestTemplate testRestTemplate;

    public ResponseEntity<Long> createCustomer(CreateCustomerRequest request) {
        return testRestTemplate.exchange(
                String.format("%s%s", localServerResolver.getLocalURL(), CUSTOMERS_API),
                HttpMethod.POST,
                new HttpEntity<>(request),
                Long.class);

    }

    public ResponseEntity<CustomerBillResponse> calculateMonthlyBill(String jwtToken, Long customerId) {
        return testRestTemplate.exchange(
                String.format("%s%s/{id}/bill", localServerResolver.getLocalURL(), CUSTOMERS_API),
                HttpMethod.GET,
                new HttpEntity<>(AuthorizationHeaderHelper.getHeaders(jwtToken)),
                CustomerBillResponse.class,
                customerId);
    }
}
