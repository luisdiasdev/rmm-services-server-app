package com.luisdias.rmmservice.modules.service.repository;

import com.luisdias.rmmservice.modules.service.entity.AvailableService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableServiceRepository extends CrudRepository<AvailableService, Long> {
}
