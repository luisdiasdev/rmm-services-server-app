package com.luisdias.rmmservice.modules.service.usecase;

import com.luisdias.rmmservice.modules.service.repository.CustomerServiceRepository;
import com.luisdias.rmmservice.modules.shared.exception.EntityNotFoundException;
import com.luisdias.rmmservice.modules.shared.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RemoveServiceFromCustomerUseCaseTest {

    RemoveServiceFromCustomerUseCase useCase;

    final Long validCustomerServiceId = 100L;
    final Long invalidCustomerServiceId = 102L;
    final Long customerId = 150L;

    @BeforeEach
    void setup() {
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        CustomerServiceRepository customerServiceRepository = mock(CustomerServiceRepository.class);

        useCase = new RemoveServiceFromCustomerUseCase(
                authenticationService,
                customerServiceRepository);

        when(authenticationService.getAuthenticatedCustomerId())
                .thenReturn(customerId);
        when(customerServiceRepository.existsByCustomerIdAndServiceId(eq(customerId), eq(validCustomerServiceId)))
                .thenReturn(true);
        when(customerServiceRepository.existsByCustomerIdAndServiceId(eq(customerId), eq(invalidCustomerServiceId)))
                .thenReturn(false);
    }

    @Test
    void shouldThrowExceptionIfResourceNotFound() {
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> useCase.remove(invalidCustomerServiceId));
    }

    @Test
    void shouldRemoveCustomerServiceIfExists() {
        assertThatCode(() -> useCase.remove(validCustomerServiceId))
                .doesNotThrowAnyException();
    }
}