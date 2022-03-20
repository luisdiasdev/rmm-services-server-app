package com.luisdias.rmmservice.modules.customer.usecase;

import com.luisdias.rmmservice.modules.customer.entity.Customer;
import com.luisdias.rmmservice.modules.customer.repository.CustomerRepository;
import com.luisdias.rmmservice.modules.shared.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindCustomerByIdUseCaseTest {

    private FindCustomerByIdUseCase useCase;

    private final Long invalidCustomerId = 1L;
    private final Long validCustomerId = 2L;

    @BeforeEach
    public void setup() {
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        useCase = new FindCustomerByIdUseCase(customerRepository);

        when(customerRepository.findById(eq(invalidCustomerId))).thenReturn(Optional.empty());
        when(customerRepository.findById(eq(validCustomerId))).thenReturn(Optional.of(new Customer()));
    }

    @Test
    void shouldThrowExceptionIfResourceNotFound() {
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> useCase.findById(invalidCustomerId));
    }

    @Test
    void shouldReturnCustomer() {
        assertThat(useCase.findById(validCustomerId)).isNotNull();
    }
}