package com.luisdias.rmmservice.modules.device.usecase;

import com.luisdias.rmmservice.modules.device.repository.DeviceRepository;
import com.luisdias.rmmservice.modules.shared.exception.EntityNotFoundException;
import com.luisdias.rmmservice.modules.shared.service.AuthenticationService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RemoveDeviceUseCase {

    private final AuthenticationService authenticationService;
    private final DeviceRepository deviceRepository;

    public RemoveDeviceUseCase(AuthenticationService authenticationService, DeviceRepository deviceRepository) {
        this.authenticationService = authenticationService;
        this.deviceRepository = deviceRepository;
    }

    @Transactional
    public void remove(Long id) {
        this.deviceRepository.findByIdAndCustomerId(id, authenticationService.getAuthenticatedCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Device"));
        this.deviceRepository.deleteByIdAndCustomerId(id, authenticationService.getAuthenticatedCustomerId());
    }
}
