package com.luisdias.rmmservice.modules.customer.repository;

import com.luisdias.rmmservice.modules.customer.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findByUsername(String username);
}
