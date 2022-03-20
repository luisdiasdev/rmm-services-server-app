package com.luisdias.rmmservice.modules.device.usecase;

import com.luisdias.rmmservice.modules.device.entity.Device;
import com.luisdias.rmmservice.modules.device.repository.DeviceRepository;
import com.luisdias.rmmservice.modules.shared.exception.EntityNotFoundException;
import com.luisdias.rmmservice.modules.shared.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RemoveDeviceUseCaseTest {

    RemoveDeviceUseCase useCase;

    final Long authenticatedCustomerId = 100L;
    final Long validDeviceId = 200L;
    final Long invalidDeviceId = 201L;

    @BeforeEach
    void setup() {
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);

        useCase = new RemoveDeviceUseCase(
                authenticationService,
                deviceRepository);

        when(authenticationService.getAuthenticatedUserId())
                .thenReturn(authenticatedCustomerId);

        when(deviceRepository.findByIdAndCustomerId(eq(validDeviceId), eq(authenticatedCustomerId)))
                .thenReturn(Optional.of(new Device()));
        when(deviceRepository.findByIdAndCustomerId(eq(invalidDeviceId), eq(authenticatedCustomerId)))
                .thenReturn(Optional.empty());
    }

    @Test
    void shouldThrowExceptionIfResourceNotFound() {
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> useCase.remove(invalidDeviceId));
    }

    @Test
    void shouldRemoveDeviceIfExists() {
        assertThatCode(() -> useCase.remove(validDeviceId))
                .doesNotThrowAnyException();
    }
}