package com.luisdias.rmmservice.modules.customer.usecase;

import com.luisdias.rmmservice.modules.customer.api.response.CustomerBillResponse;
import com.luisdias.rmmservice.modules.customer.entity.Customer;
import com.luisdias.rmmservice.modules.device.api.response.DeviceResponse;
import com.luisdias.rmmservice.modules.device.usecase.FindAllDevicesUseCase;
import com.luisdias.rmmservice.modules.device.usecase.GetDevicePriceUseCase;
import com.luisdias.rmmservice.modules.service.entity.CustomerService;
import com.luisdias.rmmservice.modules.service.usecase.FindAllCustomerServicesUseCase;
import com.luisdias.rmmservice.modules.shared.enums.OperatingSystem;
import com.luisdias.rmmservice.modules.shared.exception.UnauthorizedException;
import com.luisdias.rmmservice.modules.shared.service.AuthenticationService;
import com.luisdias.rmmservice.support.AvailableServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CalculateCustomerBillUseCaseTest {

    private CalculateCustomerBillUseCase useCase;

    private final Long authenticatedCustomerId = 100L;
    private final Long otherCustomerId = 105L;

    @BeforeEach
    public void setup() {
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        FindAllDevicesUseCase findAllDevicesUseCase = mock(FindAllDevicesUseCase.class);
        FindAllCustomerServicesUseCase findAllCustomerServicesUseCase = mock(FindAllCustomerServicesUseCase.class);
        GetDevicePriceUseCase getDevicePriceUseCase = new GetDevicePriceUseCase(); // Not mocked because of simplicity

        useCase = new CalculateCustomerBillUseCase(
                authenticationService,
                findAllDevicesUseCase,
                findAllCustomerServicesUseCase,
                getDevicePriceUseCase);

        when(authenticationService.getAuthenticatedUserId())
                .thenReturn(authenticatedCustomerId);

        when(findAllDevicesUseCase.findAll())
                .thenReturn(List.of(
                        new DeviceResponse(1L, "system-1", OperatingSystem.MAC),
                        new DeviceResponse(2L, "system-2", OperatingSystem.MAC),
                        new DeviceResponse(3L, "system-3", OperatingSystem.MAC),
                        new DeviceResponse(4L, "system-4", OperatingSystem.WINDOWS),
                        new DeviceResponse(5L, "system-5", OperatingSystem.WINDOWS)));
        Customer customer = new Customer();
        customer.setId(authenticatedCustomerId);
        when(findAllCustomerServicesUseCase.findAll())
                .thenReturn(List.of(
                        new CustomerService(customer, AvailableServiceFactory.createAntivirus()),
                        new CustomerService(customer, AvailableServiceFactory.createCloudberry()),
                        new CustomerService(customer, AvailableServiceFactory.createTeamViewer())));
    }

    @Test
    void shouldThrowUnauthorizedIfCustomerIdIsNotSameAsAuthenticated() {
        assertThatExceptionOfType(UnauthorizedException.class)
                .isThrownBy(() -> useCase.calculateBill(otherCustomerId));
    }

    @Test
    void shouldCalculateCustomerBill() {
        assertThat(useCase.calculateBill(authenticatedCustomerId))
                .extracting(CustomerBillResponse::getCostInCents)
                .isEqualTo(7100L);
    }
}