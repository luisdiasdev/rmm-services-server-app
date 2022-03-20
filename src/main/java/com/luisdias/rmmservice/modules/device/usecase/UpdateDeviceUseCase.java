package com.luisdias.rmmservice.modules.device.usecase;

import com.luisdias.rmmservice.modules.device.api.request.CreateUpdateDeviceRequest;
import com.luisdias.rmmservice.modules.device.api.response.DeviceResponse;
import com.luisdias.rmmservice.modules.device.repository.DeviceRepository;
import com.luisdias.rmmservice.modules.shared.exception.EntityNotFoundException;
import com.luisdias.rmmservice.modules.shared.service.AuthenticationService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UpdateDeviceUseCase {

    private final AuthenticationService authenticationService;
    private final DeviceRepository deviceRepository;

    public UpdateDeviceUseCase(AuthenticationService authenticationService, DeviceRepository deviceRepository) {
        this.authenticationService = authenticationService;
        this.deviceRepository = deviceRepository;
    }

    @Transactional
    public DeviceResponse update(Long id, CreateUpdateDeviceRequest request) {
        var device = this.deviceRepository
                .findByIdAndCustomerId(id, authenticationService.getAuthenticatedCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Device"));
        device.setSystemName(request.getSystemName());
        device.setOperatingSystem(request.getOperatingSystem());
        this.deviceRepository.save(device);
        return device.toResponse();
    }
}
