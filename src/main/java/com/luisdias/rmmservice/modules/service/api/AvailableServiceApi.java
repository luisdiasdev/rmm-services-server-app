package com.luisdias.rmmservice.modules.service.api;

import com.luisdias.rmmservice.modules.service.api.request.CreateAvailableServiceRequest;
import com.luisdias.rmmservice.modules.service.usecase.CreateAvailableServiceUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/available-services")
@Tag(name = "available-services")
public class AvailableServiceApi {

    private final CreateAvailableServiceUseCase createAvailableServiceUseCase;

    public AvailableServiceApi(CreateAvailableServiceUseCase createAvailableServiceUseCase) {
        this.createAvailableServiceUseCase = createAvailableServiceUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody CreateAvailableServiceRequest request) {
        createAvailableServiceUseCase.create(request);
        return ResponseEntity.ok().build();
    }
}
