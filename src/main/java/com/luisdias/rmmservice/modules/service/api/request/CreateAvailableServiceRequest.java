package com.luisdias.rmmservice.modules.service.api.request;

import java.util.List;

public class CreateAvailableServiceRequest {

    private String name;
    private String description;
    private List<PricingPolicy> pricingPolicies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PricingPolicy> getPricingPolicies() {
        return pricingPolicies;
    }

    public void setPricingPolicies(List<PricingPolicy> pricingPolicies) {
        this.pricingPolicies = pricingPolicies;
    }

    public CreateAvailableServiceRequest() {}

    public static class PricingPolicy {
        private Long price;
        private String os;

        public PricingPolicy() {
        }

        public Long getPrice() {
            return price;
        }

        public void setPrice(Long price) {
            this.price = price;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }
    }
}
