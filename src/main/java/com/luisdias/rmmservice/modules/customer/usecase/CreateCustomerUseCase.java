package com.luisdias.rmmservice.modules.customer.usecase;

import com.luisdias.rmmservice.modules.customer.api.request.CreateCustomerRequest;
import com.luisdias.rmmservice.modules.customer.entity.Customer;
import com.luisdias.rmmservice.modules.customer.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CreateCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateCustomerUseCase(CustomerRepository customerRepository,
                                 PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Long create(CreateCustomerRequest request) {
        Customer customer = new Customer();
        // TODO: Validate username before persisting
        customer.setUsername(request.getUsername());
        customer.setPassword(request.getPassword());
        customer.encodePassword(passwordEncoder::encode);
        customerRepository.save(customer);
        return customer.getId();
    }
}
