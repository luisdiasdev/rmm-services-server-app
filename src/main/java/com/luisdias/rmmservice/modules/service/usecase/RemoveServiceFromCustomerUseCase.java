package com.luisdias.rmmservice.modules.service.usecase;

import com.luisdias.rmmservice.modules.service.repository.CustomerServiceRepository;
import com.luisdias.rmmservice.modules.shared.exception.EntityNotFoundException;
import com.luisdias.rmmservice.modules.shared.service.AuthenticationService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RemoveServiceFromCustomerUseCase {

    private final AuthenticationService authenticationService;
    private final CustomerServiceRepository customerServiceRepository;

    public RemoveServiceFromCustomerUseCase(AuthenticationService authenticationService,
                                            CustomerServiceRepository customerServiceRepository) {
        this.authenticationService = authenticationService;
        this.customerServiceRepository = customerServiceRepository;
    }

    @Transactional
    public void remove(Long id) {
        var customerId = authenticationService.getAuthenticatedCustomerId();
        if (!this.customerServiceRepository.existsByCustomerIdAndServiceId(customerId, id)) {
            throw new EntityNotFoundException("Customer Service");
        }
        this.customerServiceRepository.deleteByIdAndCustomerId(id, customerId);
    }
}
