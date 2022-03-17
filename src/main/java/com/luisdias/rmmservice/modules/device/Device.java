package com.luisdias.rmmservice.modules.device;

import com.luisdias.rmmservice.modules.shared.OperatingSystem;

import javax.persistence.*;

@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "system_name", nullable = false)
    private String systemName;

    @Column(name = "os", nullable = false)
    @Enumerated(EnumType.STRING)
    private OperatingSystem operatingSystem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
