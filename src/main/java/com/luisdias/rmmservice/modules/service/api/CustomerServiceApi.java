package com.luisdias.rmmservice.modules.service.api;

import com.luisdias.rmmservice.modules.service.api.request.AddServiceToCustomerRequest;
import com.luisdias.rmmservice.modules.service.entity.CustomerService;
import com.luisdias.rmmservice.modules.service.usecase.AddServiceToCustomerUseCase;
import com.luisdias.rmmservice.modules.service.usecase.FindAllCustomerServicesUseCase;
import com.luisdias.rmmservice.modules.service.usecase.RemoveServiceFromCustomerUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/services")
@Tag(name = "services", description = "Manage customer services")
public class CustomerServiceApi {

    private final AddServiceToCustomerUseCase addServiceToCustomerUseCase;
    private final RemoveServiceFromCustomerUseCase removeServiceFromCustomerUseCase;
    private final FindAllCustomerServicesUseCase findAllCustomerServicesUseCase;

    public CustomerServiceApi(AddServiceToCustomerUseCase addServiceToCustomerUseCase,
                              RemoveServiceFromCustomerUseCase removeServiceFromCustomerUseCase,
                              FindAllCustomerServicesUseCase findAllCustomerServicesUseCase) {
        this.addServiceToCustomerUseCase = addServiceToCustomerUseCase;
        this.removeServiceFromCustomerUseCase = removeServiceFromCustomerUseCase;
        this.findAllCustomerServicesUseCase = findAllCustomerServicesUseCase;
    }

    @GetMapping
    @Operation(summary = "Gets all services registered in the customer account")
    public List<CustomerService> getAll() {
        return findAllCustomerServicesUseCase.findAll();
    }

    @PostMapping
    @Operation(summary = "Adds a new service to the customer account")
    @ApiResponse(responseCode = "401", description = "Not authorized to create the given resource")
    @ApiResponse(responseCode = "404", description = "No service and/or customer found for the given id")
    @ApiResponse(responseCode = "422", description = "Service already registered for the given customer")
    public ResponseEntity<?> create(@RequestBody AddServiceToCustomerRequest request) {
        var customerServiceId = addServiceToCustomerUseCase.add(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(customerServiceId);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Removes a service from the customer account")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "404", description = "No service found with given id")
    public void delete(@PathVariable Long id) {
        removeServiceFromCustomerUseCase.remove(id);
    }
}
