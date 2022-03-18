package com.luisdias.rmmservice.modules.device.usecase;

import com.luisdias.rmmservice.modules.customer.usecase.FindCustomerByIdUseCase;
import com.luisdias.rmmservice.modules.device.api.request.CreateUpdateDeviceRequest;
import com.luisdias.rmmservice.modules.device.api.response.DeviceResponse;
import com.luisdias.rmmservice.modules.device.entity.Device;
import com.luisdias.rmmservice.modules.device.repository.DeviceRepository;
import com.luisdias.rmmservice.modules.shared.service.AuthenticationService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CreateDeviceUseCase {

    private final AuthenticationService authenticationService;
    private final DeviceRepository deviceRepository;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;

    public CreateDeviceUseCase(AuthenticationService authenticationService,
                               DeviceRepository deviceRepository,
                               FindCustomerByIdUseCase findCustomerByIdUseCase) {
        this.authenticationService = authenticationService;
        this.deviceRepository = deviceRepository;
        this.findCustomerByIdUseCase = findCustomerByIdUseCase;
    }

    @Transactional
    public DeviceResponse create(CreateUpdateDeviceRequest request) {
        Device device = new Device();
        device.setOperatingSystem(request.getOperatingSystem());
        device.setSystemName(request.getSystemName());
        device.setCustomer(findCustomerByIdUseCase.findById(authenticationService.getAuthenticatedUserId()));
        deviceRepository.save(device);
        return device.toResponse();
    }
}
