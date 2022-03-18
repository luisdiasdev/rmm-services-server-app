package com.luisdias.rmmservice.modules.service.api.request;

public class AddServiceToCustomerRequest {

    private Long serviceId;
    private Long customerId;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
