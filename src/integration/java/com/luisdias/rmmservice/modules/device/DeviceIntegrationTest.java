package com.luisdias.rmmservice.modules.device;

import com.luisdias.rmmservice.modules.device.api.request.CreateUpdateDeviceRequest;
import com.luisdias.rmmservice.modules.device.api.response.DeviceResponse;
import com.luisdias.rmmservice.modules.shared.enums.OperatingSystem;
import com.luisdias.rmmservice.support.AbstractIT;
import com.luisdias.rmmservice.support.data.Customers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.luisdias.rmmservice.support.JwtTokenExtractor.extractJwtTokenFromResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class DeviceIntegrationTest extends AbstractIT {

    @DisplayName("should get one, get all, create, update and delete devices")
    @Test
    void shouldDoAllOperationsOnDevice() {
        var jwtToken = extractJwtTokenFromResponse(
                loginClient.doLogin(Customers.Existing.USERNAME, Customers.Existing.PASSWORD));

        // Get all
        var allDevicesResponse = deviceClient.getAllDevices(jwtToken);
        assertThat(allDevicesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        var allDevices = allDevicesResponse.getBody();
        assertThat(allDevices).isNotEmpty();
        var oneDevice = allDevices.get(0);

        // get one by id
        var oneDeviceResponse = deviceClient.getOneDevice(jwtToken, oneDevice.getId());
        assertThat(oneDeviceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(oneDeviceResponse.getBody())
                .extracting(DeviceResponse::getOperatingSystem, DeviceResponse::getSystemName)
                .contains(oneDevice.getOperatingSystem(), oneDevice.getSystemName());

        // create
        var newDeviceRequest = new CreateUpdateDeviceRequest();
        newDeviceRequest.setOperatingSystem(OperatingSystem.MAC);
        newDeviceRequest.setSystemName("windows-luis-01");
        var newDeviceResponse = deviceClient.addDevice(jwtToken, newDeviceRequest);
        assertThat(newDeviceResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(newDeviceResponse.getBody()).isNotNull();
        var newDevice = newDeviceResponse.getBody();

        // update one by id
        var updateDeviceRequest = new CreateUpdateDeviceRequest();
        updateDeviceRequest.setSystemName("new-system-02");
        updateDeviceRequest.setOperatingSystem(OperatingSystem.WINDOWS);
        var updatedDeviceResponse = deviceClient.updateDevice(jwtToken, newDevice.getId(), updateDeviceRequest);
        assertThat(updatedDeviceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updatedDeviceResponse.getBody()).isNotEqualTo(newDevice);

        // delete by id
        ResponseEntity<?> deleteResponse = deviceClient.deleteDevice(jwtToken, newDevice.getId());
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
