package com.luisdias.rmmservice.modules.device.api.response;

import com.luisdias.rmmservice.modules.shared.enums.OperatingSystem;

public class DeviceResponse {

    private final Long id;
    private final String systemName;
    private final OperatingSystem operatingSystem;

    public DeviceResponse(Long id, String systemName, OperatingSystem operatingSystem) {
        this.id = id;
        this.systemName = systemName;
        this.operatingSystem = operatingSystem;
    }

    public Long getId() {
        return id;
    }

    public String getSystemName() {
        return systemName;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }
}
