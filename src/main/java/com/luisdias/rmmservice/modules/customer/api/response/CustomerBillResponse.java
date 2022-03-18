package com.luisdias.rmmservice.modules.customer.api.response;

public class CustomerBillResponse {

    private final Long costInCents;

    public CustomerBillResponse(Long costInCents) {
        this.costInCents = costInCents;
    }

    public Long getCostInCents() {
        return costInCents;
    }
}
