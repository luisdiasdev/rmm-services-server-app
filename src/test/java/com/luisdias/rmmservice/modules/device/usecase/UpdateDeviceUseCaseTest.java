package com.luisdias.rmmservice.modules.device.usecase;

import com.luisdias.rmmservice.modules.device.api.request.CreateUpdateDeviceRequest;
import com.luisdias.rmmservice.modules.device.api.response.DeviceResponse;
import com.luisdias.rmmservice.modules.device.entity.Device;
import com.luisdias.rmmservice.modules.device.repository.DeviceRepository;
import com.luisdias.rmmservice.modules.shared.enums.OperatingSystem;
import com.luisdias.rmmservice.modules.shared.exception.EntityNotFoundException;
import com.luisdias.rmmservice.modules.shared.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UpdateDeviceUseCaseTest {

    UpdateDeviceUseCase useCase;
    CreateUpdateDeviceRequest request;

    final Long authenticatedCustomerId = 100L;
    final Long validDeviceId = 200L;
    final Long invalidDeviceId = 201L;
    final String systemName = "system-42";


    @BeforeEach
    void setup() {
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);

        useCase = new UpdateDeviceUseCase(
                authenticationService,
                deviceRepository);

        when(authenticationService.getAuthenticatedCustomerId())
                .thenReturn(authenticatedCustomerId);

        when(deviceRepository.findByIdAndCustomerId(eq(validDeviceId), eq(authenticatedCustomerId)))
                .thenReturn(Optional.of(new Device()));
        when(deviceRepository.findByIdAndCustomerId(eq(invalidDeviceId), eq(authenticatedCustomerId)))
                .thenReturn(Optional.empty());

        request = new CreateUpdateDeviceRequest();
        request.setOperatingSystem(OperatingSystem.MAC);
        request.setSystemName(systemName);
    }

    @Test
    void shouldThrowExceptionIfResourceNotFound() {
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> useCase.update(invalidDeviceId, request));
    }

    @Test
    void shouldUpdateDeviceIfExists() {
        assertThat(useCase.update(validDeviceId, request))
                .isInstanceOf(DeviceResponse.class)
                .extracting("operatingSystem", "systemName")
                .containsExactly(OperatingSystem.MAC, systemName);
    }
}