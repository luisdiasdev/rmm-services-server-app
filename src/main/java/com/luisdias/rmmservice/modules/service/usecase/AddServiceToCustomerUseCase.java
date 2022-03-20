package com.luisdias.rmmservice.modules.service.usecase;

import com.luisdias.rmmservice.modules.customer.usecase.FindCustomerByIdUseCase;
import com.luisdias.rmmservice.modules.service.api.request.AddServiceToCustomerRequest;
import com.luisdias.rmmservice.modules.service.entity.CustomerService;
import com.luisdias.rmmservice.modules.service.repository.CustomerServiceRepository;
import com.luisdias.rmmservice.modules.shared.exception.UnauthorizedException;
import com.luisdias.rmmservice.modules.shared.exception.ValidationException;
import com.luisdias.rmmservice.modules.shared.service.AuthenticationService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class AddServiceToCustomerUseCase {

    private final AuthenticationService authenticationService;
    private final CustomerServiceRepository customerServiceRepository;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;
    private final FindAvailableServiceByIdUseCase findAvailableServiceByIdUseCase;

    public AddServiceToCustomerUseCase(AuthenticationService authenticationService,
                                       CustomerServiceRepository customerServiceRepository,
                                       FindCustomerByIdUseCase findCustomerByIdUseCase,
                                       FindAvailableServiceByIdUseCase findAvailableServiceByIdUseCase) {
        this.authenticationService = authenticationService;
        this.customerServiceRepository = customerServiceRepository;
        this.findCustomerByIdUseCase = findCustomerByIdUseCase;
        this.findAvailableServiceByIdUseCase = findAvailableServiceByIdUseCase;
    }

    @Transactional
    public Long add(AddServiceToCustomerRequest request) {
        if (!Objects.equals(request.getCustomerId(), authenticationService.getAuthenticatedCustomerId())) {
            throw new UnauthorizedException();
        }
        if (customerServiceRepository.existsByCustomerIdAndServiceId(request.getCustomerId(), request.getServiceId())) {
            throw new ValidationException("Service already registered for the customer");
        }
        var customer = findCustomerByIdUseCase.findById(authenticationService.getAuthenticatedCustomerId());
        var service = findAvailableServiceByIdUseCase.findById(request.getServiceId());
        var customerService = new CustomerService(customer, service);
        customerServiceRepository.save(customerService);
        return customerService.getId();
    }
}
