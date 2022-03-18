package com.luisdias.rmmservice.modules.device.usecase;

import com.luisdias.rmmservice.modules.device.api.response.DeviceResponse;
import com.luisdias.rmmservice.modules.device.entity.Device;
import com.luisdias.rmmservice.modules.device.repository.DeviceRepository;
import com.luisdias.rmmservice.modules.shared.exception.EntityNotFoundException;
import com.luisdias.rmmservice.modules.shared.service.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class FindDeviceByIdUseCase {

    private final AuthenticationService authenticationService;
    private final DeviceRepository deviceRepository;

    public FindDeviceByIdUseCase(AuthenticationService authenticationService,
                                 DeviceRepository deviceRepository) {
        this.authenticationService = authenticationService;
        this.deviceRepository = deviceRepository;
    }

    public DeviceResponse findById(Long id) {
        return this.deviceRepository.findByIdAndCustomerId(id, authenticationService.getAuthenticatedUserId())
                .map(Device::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Device"));
    }
}
