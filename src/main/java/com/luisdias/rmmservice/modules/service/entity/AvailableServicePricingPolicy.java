package com.luisdias.rmmservice.modules.service.entity;

import com.luisdias.rmmservice.modules.shared.enums.OperatingSystem;

import javax.persistence.*;

@Entity
@Table(name = "available_service_pricing_policy")
public class AvailableServicePricingPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "avail_serv_pol_id_seq")
    @SequenceGenerator(name = "avail_serv_pol_id_seq", sequenceName = "avail_serv_pol_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "available_service_id", foreignKey = @ForeignKey(name = "fk_avail_serv_pol"))
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

    public AvailableService getAvailableService() {
        return availableService;
    }

    public void setAvailableService(AvailableService availableService) {
        this.availableService = availableService;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }
}