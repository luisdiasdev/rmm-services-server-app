package com.luisdias.rmmservice.modules.device.api.request;

import com.luisdias.rmmservice.modules.shared.enums.OperatingSystem;

public class CreateUpdateDeviceRequest {

    private String systemName;
    private OperatingSystem operatingSystem;

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
}