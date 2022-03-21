package com.luisdias.rmmservice.modules.service.entity;

import com.luisdias.rmmservice.modules.customer.entity.Customer;
import com.luisdias.rmmservice.modules.service.api.request.CustomerServiceResponse;

import javax.persistence.*;

@Entity
@Table(name = "customer_service",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_customer_service", columnNames = {"customer_id", "service_id"})
        })
public class CustomerService {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_service_id_seq")
    @SequenceGenerator(name = "customer_service_id_seq", sequenceName = "customer_service_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_service_customer"))
    private Customer customer;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", foreignKey = @ForeignKey(name = "fk_customer_available_serv"))
    private AvailableService service;

    public CustomerService() {
    }

    public CustomerService(Customer customer, AvailableService service) {
        this.customer = customer;
        this.service = service;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AvailableService getService() {
        return service;
    }

    public void setService(AvailableService service) {
        this.service = service;
    }

    public CustomerServiceResponse toResponse() {
        return new CustomerServiceResponse(this.getCustomer().getId(), this.getService().getId());
    }
}
