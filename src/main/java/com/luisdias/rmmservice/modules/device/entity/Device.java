package com.luisdias.rmmservice.modules.device.entity;

import com.luisdias.rmmservice.modules.customer.entity.Customer;
import com.luisdias.rmmservice.modules.device.api.response.DeviceResponse;
import com.luisdias.rmmservice.modules.shared.enums.OperatingSystem;

import javax.persistence.*;

@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device_id_seq")
    @SequenceGenerator(name = "device_id_seq", sequenceName = "device_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "system_name", nullable = false)
    private String systemName;

    @Column(name = "os", nullable = false)
    @Enumerated(EnumType.STRING)
    private OperatingSystem operatingSystem;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_device_customer"))
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public DeviceResponse toResponse() {
        return new DeviceResponse(this.id, this.getSystemName(), this.getOperatingSystem());
    }
}
