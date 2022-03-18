package com.luisdias.rmmservice.modules.device.repository;

import com.luisdias.rmmservice.modules.device.entity.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {

    List<Device> findAllByCustomerId(Long customerId);

    Optional<Device> findByIdAndCustomerId(Long id, Long customerId);

    Integer deleteByIdAndCustomerId(Long id, Long customerId);
}
