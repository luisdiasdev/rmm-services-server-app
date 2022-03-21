package com.luisdias.rmmservice.modules.service.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CustomerServiceResponse {

    private final Long customerId;
    private final Long serviceId;

    @JsonCreator
    public CustomerServiceResponse(Long customerId, Long serviceId) {
        this.customerId = customerId;
        this.serviceId = serviceId;
    }

    public Long getCustomerId() {
        return customerId;
    }
    public Long getServiceId() {
        return serviceId;
    }
}
