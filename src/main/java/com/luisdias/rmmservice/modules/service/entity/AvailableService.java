package com.luisdias.rmmservice.modules.service.entity;

import com.luisdias.rmmservice.modules.shared.enums.OperatingSystem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "available_service",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_available_service_name", columnNames = {"name"})
        })
public class AvailableService {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "avail_serv_id_seq")
    @SequenceGenerator(name = "avail_serv_id_seq", sequenceName = "avail_serv_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "availableService", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public boolean hasPolicyForOperatingSystem(OperatingSystem operatingSystem) {
        return this.getPricingPolicies()
                .stream().anyMatch(policy -> policy.getOperatingSystem().equals(operatingSystem));
    }
}
