package com.luisdias.rmmservice.modules.service.usecase;

import com.luisdias.rmmservice.modules.customer.entity.Customer;
import com.luisdias.rmmservice.modules.service.entity.AvailableService;
import com.luisdias.rmmservice.modules.service.entity.CustomerService;
import com.luisdias.rmmservice.modules.service.repository.CustomerServiceRepository;
import com.luisdias.rmmservice.modules.shared.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindAllCustomerServicesUseCaseTest {

    FindAllCustomerServicesUseCase useCase;

    final Long customerId = 100L;

    @BeforeEach
    void setup() {
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        CustomerServiceRepository customerServiceRepository = mock(CustomerServiceRepository.class);

        useCase = new FindAllCustomerServicesUseCase(
                authenticationService,
                customerServiceRepository);

        when(authenticationService.getAuthenticatedCustomerId()).thenReturn(customerId);
        when(customerServiceRepository.findAllByCustomerId(eq(customerId)))
                .thenReturn(List.of(new CustomerService(new Customer(), new AvailableService())));
    }

    @Test
    void shouldReturnListOfCustomerServices() {
        assertThat(useCase.findAll()).isNotEmpty();
    }
}