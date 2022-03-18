package com.luisdias.rmmservice.modules.service.usecase;

import com.luisdias.rmmservice.modules.service.entity.AvailableService;
import com.luisdias.rmmservice.modules.service.repository.AvailableServiceRepository;
import com.luisdias.rmmservice.modules.shared.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAvailableServiceByIdUseCase {

    private final AvailableServiceRepository availableServiceRepository;

    public FindAvailableServiceByIdUseCase(AvailableServiceRepository availableServiceRepository) {
        this.availableServiceRepository = availableServiceRepository;
    }

    public AvailableService findById(Long id) {
        return availableServiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Available Service"));
    }
}
