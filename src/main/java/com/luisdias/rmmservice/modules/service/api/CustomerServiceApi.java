package com.luisdias.rmmservice.modules.service.api;

import com.luisdias.rmmservice.modules.service.api.request.AddServiceToCustomerRequest;
import com.luisdias.rmmservice.modules.service.usecase.AddServiceToCustomerUseCase;
import com.luisdias.rmmservice.modules.service.usecase.DeleteServiceFromCustomerUseCase;
import com.luisdias.rmmservice.modules.service.usecase.FindAllCustomerServicesUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/services")
@Tag(name = "services")
public class CustomerServiceApi {

    private final AddServiceToCustomerUseCase addServiceToCustomerUseCase;
    private final DeleteServiceFromCustomerUseCase deleteServiceFromCustomerUseCase;
    private final FindAllCustomerServicesUseCase findAllCustomerServicesUseCase;

    public CustomerServiceApi(AddServiceToCustomerUseCase addServiceToCustomerUseCase,
                              DeleteServiceFromCustomerUseCase deleteServiceFromCustomerUseCase,
                              FindAllCustomerServicesUseCase findAllCustomerServicesUseCase) {
        this.addServiceToCustomerUseCase = addServiceToCustomerUseCase;
        this.deleteServiceFromCustomerUseCase = deleteServiceFromCustomerUseCase;
        this.findAllCustomerServicesUseCase = findAllCustomerServicesUseCase;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        findAllCustomerServicesUseCase.findAll();
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AddServiceToCustomerRequest request) {
        var customerServiceId = addServiceToCustomerUseCase.add(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(customerServiceId);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        deleteServiceFromCustomerUseCase.delete(id);
        return ResponseEntity.ok().build();
    }
}
