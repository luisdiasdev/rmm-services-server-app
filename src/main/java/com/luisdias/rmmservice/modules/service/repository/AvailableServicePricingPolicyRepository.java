package com.luisdias.rmmservice.modules.service.repository;

import com.luisdias.rmmservice.modules.service.entity.AvailableServicePricingPolicy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableServicePricingPolicyRepository extends CrudRepository<AvailableServicePricingPolicy, Long> {
}
