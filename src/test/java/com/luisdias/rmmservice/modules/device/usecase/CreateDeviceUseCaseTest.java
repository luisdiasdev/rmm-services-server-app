package com.luisdias.rmmservice.modules.device.usecase;

import com.luisdias.rmmservice.modules.customer.entity.Customer;
import com.luisdias.rmmservice.modules.customer.usecase.FindCustomerByIdUseCase;
import com.luisdias.rmmservice.modules.device.api.request.CreateUpdateDeviceRequest;
import com.luisdias.rmmservice.modules.device.entity.Device;
import com.luisdias.rmmservice.modules.device.repository.DeviceRepository;
import com.luisdias.rmmservice.modules.shared.enums.OperatingSystem;
import com.luisdias.rmmservice.modules.shared.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateDeviceUseCaseTest {

    CreateDeviceUseCase useCase;

    final Long authenticatedCustomerId = 100L;

    @BeforeEach
    void setup() {
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        FindCustomerByIdUseCase findCustomerByIdUseCase = mock(FindCustomerByIdUseCase.class);
        useCase = new CreateDeviceUseCase(
                authenticationService,
                deviceRepository,
                findCustomerByIdUseCase);

        when(authenticationService.getAuthenticatedCustomerId())
                .thenReturn(authenticatedCustomerId);
        when(findCustomerByIdUseCase.findById(eq(authenticatedCustomerId)))
                .thenReturn(new Customer());
        when(deviceRepository.save(any()))
                .thenAnswer(invocation -> {
                    var device = invocation.<Device>getArgument(0);
                    device.setId(1L);
                    return device;
                });
    }

    @Test
    void shouldCreateDevice() {
        CreateUpdateDeviceRequest request = new CreateUpdateDeviceRequest();
        request.setSystemName("system-1");
        request.setOperatingSystem(OperatingSystem.MAC);

        assertThat(useCase.create(request))
                .extracting("id", "operatingSystem", "systemName")
                .contains(1L, OperatingSystem.MAC, "system-1");
    }
}