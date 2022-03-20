package com.luisdias.rmmservice.support.client;

import com.luisdias.rmmservice.modules.device.api.request.CreateUpdateDeviceRequest;
import com.luisdias.rmmservice.modules.device.api.response.DeviceResponse;
import com.luisdias.rmmservice.support.AuthorizationHeaderHelper;
import com.luisdias.rmmservice.support.LocalServerResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class DeviceClient {

    private static final String DEVICES_API = "/v1/devices";

    @Autowired
    private LocalServerResolver localServerResolver;
    @Autowired
    private TestRestTemplate testRestTemplate;

    public ResponseEntity<DeviceResponse> addDevice(String jwtToken, CreateUpdateDeviceRequest request) {
        return testRestTemplate.exchange(
                String.format("%s%s", localServerResolver.getLocalURL(), DEVICES_API),
                HttpMethod.POST,
                new HttpEntity<>(request, AuthorizationHeaderHelper.getHeaders(jwtToken)),
                DeviceResponse.class);
    }

    public ResponseEntity<DeviceResponse> updateDevice(String jwtToken, Long deviceId, CreateUpdateDeviceRequest request) {
        return testRestTemplate.exchange(
                String.format("%s%s/{deviceId}", localServerResolver.getLocalURL(), DEVICES_API),
                HttpMethod.PUT,
                new HttpEntity<>(request, AuthorizationHeaderHelper.getHeaders(jwtToken)),
                DeviceResponse.class,
                deviceId);
    }

    public ResponseEntity<List<DeviceResponse>> getAllDevices(String jwtToken) {
        return testRestTemplate.exchange(
                String.format("%s%s", localServerResolver.getLocalURL(), DEVICES_API),
                HttpMethod.GET,
                new HttpEntity<>(AuthorizationHeaderHelper.getHeaders(jwtToken)),
                new ParameterizedTypeReference<>() {});
    }

    public ResponseEntity<DeviceResponse> getOneDevice(String jwtToken, Long deviceId) {
        return testRestTemplate.exchange(
                String.format("%s%s/{deviceId}", localServerResolver.getLocalURL(), DEVICES_API),
                HttpMethod.GET,
                new HttpEntity<>(AuthorizationHeaderHelper.getHeaders(jwtToken)),
                new ParameterizedTypeReference<>() {},
                deviceId);
    }

    public ResponseEntity<?> deleteDevice(String jwtToken, Long deviceId) {
        return testRestTemplate.exchange(
                String.format("%s%s/{deviceId}", localServerResolver.getLocalURL(), DEVICES_API),
                HttpMethod.DELETE,
                new HttpEntity<>(AuthorizationHeaderHelper.getHeaders(jwtToken)),
                Object.class,
                deviceId);
    }
}
