package com.luisdias.rmmservice.modules.device.api;

import com.luisdias.rmmservice.modules.device.api.request.CreateUpdateDeviceRequest;
import com.luisdias.rmmservice.modules.device.api.response.DeviceResponse;
import com.luisdias.rmmservice.modules.device.usecase.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/devices")
@Tag(name = "devices", description = "Manage customer devices")
public class DeviceApi {

    private final CreateDeviceUseCase createDeviceUseCase;
    private final UpdateDeviceUseCase updateDeviceUseCase;
    private final FindDeviceByIdUseCase findDeviceByIdUseCase;
    private final FindAllDevicesUseCase findAllDevicesUseCase;
    private final RemoveDeviceUseCase removeDeviceUseCase;

    public DeviceApi(CreateDeviceUseCase createDeviceUseCase,
                     UpdateDeviceUseCase updateDeviceUseCase,
                     FindDeviceByIdUseCase findDeviceByIdUseCase,
                     FindAllDevicesUseCase findAllDevicesUseCase,
                     RemoveDeviceUseCase removeDeviceUseCase) {
        this.createDeviceUseCase = createDeviceUseCase;
        this.updateDeviceUseCase = updateDeviceUseCase;
        this.findDeviceByIdUseCase = findDeviceByIdUseCase;
        this.findAllDevicesUseCase = findAllDevicesUseCase;
        this.removeDeviceUseCase = removeDeviceUseCase;
    }

    @GetMapping("{id}")
    @ApiResponse(responseCode = "404", description = "No device found with given id")
    public DeviceResponse getOne(@PathVariable Long id) {
        return findDeviceByIdUseCase.findById(id);
    }

    @GetMapping
    public List<DeviceResponse> getAll() {
        return findAllDevicesUseCase.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceResponse create(@RequestBody CreateUpdateDeviceRequest request) {
        return createDeviceUseCase.create(request);
    }

    @PutMapping("{id}")
    @ApiResponse(responseCode = "404", description = "No device found with given id")
    public DeviceResponse update(@PathVariable Long id, @RequestBody CreateUpdateDeviceRequest request) {
        return updateDeviceUseCase.update(id, request);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "404", description = "No device found with given id")
    public void delete(@PathVariable Long id) {
        removeDeviceUseCase.remove(id);
    }
}
