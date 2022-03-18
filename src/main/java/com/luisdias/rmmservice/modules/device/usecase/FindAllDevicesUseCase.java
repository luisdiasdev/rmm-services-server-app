package com.luisdias.rmmservice.modules.device.usecase;

import com.luisdias.rmmservice.modules.device.api.response.DeviceResponse;
import com.luisdias.rmmservice.modules.device.entity.Device;
import com.luisdias.rmmservice.modules.device.repository.DeviceRepository;
import com.luisdias.rmmservice.modules.shared.service.AuthenticationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindAllDevicesUseCase {

    private final AuthenticationService authenticationService;
    private final DeviceRepository deviceRepository;

    public FindAllDevicesUseCase(AuthenticationService authenticationService,
                                 DeviceRepository deviceRepository) {
        this.authenticationService = authenticationService;
        this.deviceRepository = deviceRepository;
    }

    public List<DeviceResponse> findAll() {
        return deviceRepository.findAllByCustomerId(authenticationService.getAuthenticatedUserId())
                .stream()
                .map(Device::toResponse)
                .collect(Collectors.toList());
    }
}
