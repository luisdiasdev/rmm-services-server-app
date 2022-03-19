package com.luisdias.rmmservice.modules.service.repository;

import com.luisdias.rmmservice.modules.service.entity.CustomerService;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerServiceRepository extends CrudRepository<CustomerService, Long> {

    boolean existsByCustomerIdAndServiceId(Long customerId, Long serviceId);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"service", "service.pricingPolicies"})
    List<CustomerService> findAllByCustomerId(Long customerId);

    Integer deleteByIdAndCustomerId(Long id, Long customerId);
}
