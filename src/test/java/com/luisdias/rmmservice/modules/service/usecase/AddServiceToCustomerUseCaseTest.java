package com.luisdias.rmmservice.modules.service.usecase;

import com.luisdias.rmmservice.modules.customer.entity.Customer;
import com.luisdias.rmmservice.modules.customer.usecase.FindCustomerByIdUseCase;
import com.luisdias.rmmservice.modules.service.api.request.AddServiceToCustomerRequest;
import com.luisdias.rmmservice.modules.service.entity.CustomerService;
import com.luisdias.rmmservice.modules.service.repository.CustomerServiceRepository;
import com.luisdias.rmmservice.modules.shared.exception.UnauthorizedException;
import com.luisdias.rmmservice.modules.shared.exception.ValidationException;
import com.luisdias.rmmservice.modules.shared.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddServiceToCustomerUseCaseTest {

    AddServiceToCustomerUseCase useCase;
    AuthenticationService authenticationService;

    AddServiceToCustomerRequest wrongCustomerRequest;
    AddServiceToCustomerRequest newServiceRequest;
    AddServiceToCustomerRequest addExistingServiceRequest;

    final Long newServiceId = 100L;
    final Long existingServiceId = 101L;
    final Long validCustomerId = 100L;
    final Long invalidCustomerId = 120L;

    @BeforeEach
    void setup() {
        authenticationService = mock(AuthenticationService.class);
        CustomerServiceRepository customerServiceRepository = mock(CustomerServiceRepository.class);
        FindCustomerByIdUseCase findCustomerByIdUseCase = mock(FindCustomerByIdUseCase.class);
        FindAvailableServiceByIdUseCase findAvailableServiceByIdUseCase = mock(FindAvailableServiceByIdUseCase.class);

        useCase = new AddServiceToCustomerUseCase(
                authenticationService,
                customerServiceRepository,
                findCustomerByIdUseCase,
                findAvailableServiceByIdUseCase);

        wrongCustomerRequest = new AddServiceToCustomerRequest();
        wrongCustomerRequest.setServiceId(newServiceId);
        wrongCustomerRequest.setCustomerId(invalidCustomerId);

        newServiceRequest = new AddServiceToCustomerRequest();
        newServiceRequest.setServiceId(newServiceId);
        newServiceRequest.setCustomerId(validCustomerId);

        addExistingServiceRequest = new AddServiceToCustomerRequest();
        addExistingServiceRequest.setServiceId(existingServiceId);
        addExistingServiceRequest.setCustomerId(validCustomerId);

        when(authenticationService.getAuthenticatedCustomerId())
                .thenReturn(validCustomerId);

        when(customerServiceRepository.existsByCustomerIdAndServiceId(eq(validCustomerId), eq(existingServiceId)))
                .thenReturn(true);

        when(findCustomerByIdUseCase.findById(eq(validCustomerId)))
                .thenReturn(new Customer());

        when(customerServiceRepository.save(any()))
                .thenAnswer(invocation -> {
                    var entity = invocation.<CustomerService>getArgument(0);
                    entity.setId(1L);
                    return entity;
                });
    }

    @Test
    void shouldThrowUnauthorizedExceptionIfCustomerDoesNotMatchAuthenticatedCustomer() {
        assertThatExceptionOfType(UnauthorizedException.class)
                .isThrownBy(() -> useCase.add(wrongCustomerRequest));
    }

    @Test
    void shouldThrowValidationExceptionIfAlreadyHasServiceRegisteredForTheCustomer() {
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> useCase.add(addExistingServiceRequest));
    }

    @Test
    void shouldAddServiceToCustomerIfRequestValid() {
        var customerServiceId = useCase.add(newServiceRequest);

        assertThat(customerServiceId)
                .isEqualTo(1L);
    }
}