package com.luisdias.rmmservice.modules.customer.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CustomerBillResponse {

    private final Long costInCents;

    @JsonCreator
    public CustomerBillResponse(Long costInCents) {
        this.costInCents = costInCents;
    }

    public Long getCostInCents() {
        return costInCents;
    }
}
