package com.luisdias.rmmservice.modules.customer.usecase;

import com.luisdias.rmmservice.modules.customer.entity.Customer;
import com.luisdias.rmmservice.modules.customer.repository.CustomerRepository;
import com.luisdias.rmmservice.modules.shared.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FindCustomerByIdUseCase {

    private final CustomerRepository customerRepository;

    public FindCustomerByIdUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer"));
    }
}
