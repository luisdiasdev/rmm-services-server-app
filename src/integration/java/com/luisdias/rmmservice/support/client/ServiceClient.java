package com.luisdias.rmmservice.support.client;

import com.luisdias.rmmservice.modules.service.api.request.AddServiceToCustomerRequest;
import com.luisdias.rmmservice.modules.service.api.request.CustomerServiceResponse;
import com.luisdias.rmmservice.support.AuthorizationHeaderHelper;
import com.luisdias.rmmservice.support.LocalServerResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ServiceClient {

    private static final String SERVICES_API = "/v1/services";

    @Autowired
    private LocalServerResolver localServerResolver;
    @Autowired
    private TestRestTemplate testRestTemplate;

    public ResponseEntity<Long> addCustomerService(String jwtToken, AddServiceToCustomerRequest request) {
        return testRestTemplate.exchange(
                String.format("%s%s", localServerResolver.getLocalURL(), SERVICES_API),
                HttpMethod.POST,
                new HttpEntity<>(request, AuthorizationHeaderHelper.getHeaders(jwtToken)),
                Long.class);
    }

    public ResponseEntity<List<CustomerServiceResponse>> getAllServices(String jwtToken) {
        return testRestTemplate.exchange(
                String.format("%s%s", localServerResolver.getLocalURL(), SERVICES_API),
                HttpMethod.GET,
                new HttpEntity<>(AuthorizationHeaderHelper.getHeaders(jwtToken)),
                new ParameterizedTypeReference<>() {});
    }

    public ResponseEntity<?> deleteServiceById(String jwtToken, Long customerServiceId) {
        return testRestTemplate.exchange(
                String.format("%s%s/{serviceId}", localServerResolver.getLocalURL(), SERVICES_API),
                HttpMethod.DELETE,
                new HttpEntity<>(AuthorizationHeaderHelper.getHeaders(jwtToken)),
                Object.class,
                customerServiceId);
    }
}
