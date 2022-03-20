package com.luisdias.rmmservice.modules.customer.usecase;

import com.luisdias.rmmservice.modules.customer.api.request.CreateCustomerRequest;
import com.luisdias.rmmservice.modules.customer.entity.Customer;
import com.luisdias.rmmservice.modules.customer.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateCustomerUseCaseTest {

    private CreateCustomerUseCase useCase;

    private final Long savedEntityId = 1L;

    @BeforeEach
    void setup() {
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        useCase = new CreateCustomerUseCase(customerRepository, passwordEncoder);

        when(customerRepository.save(any()))
                .thenAnswer(invocation -> {
                    var customer = invocation.<Customer>getArgument(0);
                    customer.setId(savedEntityId);
                    return customer;
                });
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(any())).thenReturn(encodedPassword);
    }

    @Test
    void shouldCreateCustomer() {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setPassword("strongpassword");
        request.setUsername("username");

        Long createdCustomerId = useCase.create(request);

        assertThat(createdCustomerId).isEqualTo(savedEntityId);
    }

}