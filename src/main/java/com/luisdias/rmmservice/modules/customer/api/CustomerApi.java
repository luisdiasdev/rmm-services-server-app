package com.luisdias.rmmservice.modules.customer.api;

import com.luisdias.rmmservice.modules.customer.api.request.CreateCustomerRequest;
import com.luisdias.rmmservice.modules.customer.usecase.CalculateCustomerBillUseCase;
import com.luisdias.rmmservice.modules.customer.usecase.CreateCustomerUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/customers")
@Tag(name = "customers")
public class CustomerApi {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final CalculateCustomerBillUseCase calculateCustomerBillUseCase;

    public CustomerApi(CreateCustomerUseCase createCustomerUseCase,
                       CalculateCustomerBillUseCase calculateCustomerBillUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.calculateCustomerBillUseCase = calculateCustomerBillUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Tag(name = "public")
    @SecurityRequirement(name = "")
    public void create(@RequestBody CreateCustomerRequest request) {
        createCustomerUseCase.create(request);
    }

    @GetMapping("{id}/bill")
    @Operation(summary = "Calculate the bill for a given customer")
    public void calculate(@PathVariable Long id) {
        calculateCustomerBillUseCase.calculateBill(id);
    }
}
