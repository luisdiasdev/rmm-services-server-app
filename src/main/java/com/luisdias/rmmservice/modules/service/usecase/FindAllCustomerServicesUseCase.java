package com.luisdias.rmmservice.modules.service.usecase;

import com.luisdias.rmmservice.modules.service.entity.CustomerService;
import com.luisdias.rmmservice.modules.service.repository.CustomerServiceRepository;
import com.luisdias.rmmservice.modules.shared.service.AuthenticationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllCustomerServicesUseCase {

    private final AuthenticationService authenticationService;
    private final CustomerServiceRepository customerServiceRepository;

    public FindAllCustomerServicesUseCase(AuthenticationService authenticationService,
                                          CustomerServiceRepository customerServiceRepository) {
        this.authenticationService = authenticationService;
        this.customerServiceRepository = customerServiceRepository;
    }

    public List<CustomerService> findAll() {
        return customerServiceRepository
                .findAllByCustomerId(authenticationService.getAuthenticatedCustomerId());
    }
}
