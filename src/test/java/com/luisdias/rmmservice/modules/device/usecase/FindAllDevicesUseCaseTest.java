package com.luisdias.rmmservice.modules.device.usecase;

import com.luisdias.rmmservice.modules.device.entity.Device;
import com.luisdias.rmmservice.modules.device.repository.DeviceRepository;
import com.luisdias.rmmservice.modules.shared.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindAllDevicesUseCaseTest {

    FindAllDevicesUseCase useCase;

    final Long authenticatedCustomerId = 100L;

    @BeforeEach
    void setup() {
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);

        useCase = new FindAllDevicesUseCase(
                authenticationService,
                deviceRepository);

        when(authenticationService.getAuthenticatedCustomerId())
                .thenReturn(authenticatedCustomerId);

        when(deviceRepository.findAllByCustomerId(eq(authenticatedCustomerId)))
                .thenReturn(List.of(
                        new Device(),
                        new Device(),
                        new Device()
                ));
    }

    @Test
    void shouldReturnListOfDevices() {
        assertThat(useCase.findAll()).hasSize(3);
    }

}