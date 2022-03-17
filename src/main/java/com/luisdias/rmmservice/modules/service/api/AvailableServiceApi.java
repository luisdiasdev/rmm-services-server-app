package com.luisdias.rmmservice.modules.service.api;

import com.luisdias.rmmservice.modules.service.api.request.CreateAvailableServiceRequest;
import com.luisdias.rmmservice.modules.service.usecase.CreateAvailableServiceUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/available-services")
public class AvailableServiceApi {

    private final CreateAvailableServiceUseCase createAvailableServiceUseCase;

    public AvailableServiceApi(CreateAvailableServiceUseCase createAvailableServiceUseCase) {
        this.createAvailableServiceUseCase = createAvailableServiceUseCase;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateAvailableServiceRequest request) {
        createAvailableServiceUseCase.create(request);
        return ResponseEntity.ok().build();
    }
}
