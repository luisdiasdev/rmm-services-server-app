package com.luisdias.rmmservice.modules.service.entity;

import com.luisdias.rmmservice.modules.shared.OperatingSystem;

import javax.persistence.*;

@Entity
@Table(name = "available_service_pricing_policy")
public class AvailableServicePricingPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "available_service_id")
    private AvailableService availableService;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "os", nullable = false)
    @Enumerated(EnumType.STRING)
    private OperatingSystem operatingSystem;


    public AvailableServicePricingPolicy() {
    }

    public AvailableServicePricingPolicy(AvailableService availableService, Long price, OperatingSystem operatingSystem) {
        this.availableService = availableService;
        this.price = price;
        this.operatingSystem = operatingSystem;
    }

    public AvailableService getService() {
        return availableService;
    }

    public void setService(AvailableService availableService) {
        this.availableService = availableService;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}