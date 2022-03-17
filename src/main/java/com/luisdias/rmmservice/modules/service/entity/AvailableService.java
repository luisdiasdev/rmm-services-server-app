package com.luisdias.rmmservice.modules.service.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "available_service")
public class AvailableService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "availableService")
    private List<AvailableServicePricingPolicy> pricingPolicies = new ArrayList<>();

    public List<AvailableServicePricingPolicy> getPricingPolicies() {
        return pricingPolicies;
    }

    public void setPricingPolicies(List<AvailableServicePricingPolicy> pricingPolicies) {
        this.pricingPolicies = pricingPolicies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
