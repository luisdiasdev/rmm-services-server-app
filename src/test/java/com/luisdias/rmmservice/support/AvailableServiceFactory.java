package com.luisdias.rmmservice.support;

import com.luisdias.rmmservice.modules.service.entity.AvailableService;
import com.luisdias.rmmservice.modules.service.entity.AvailableServicePricingPolicy;
import com.luisdias.rmmservice.modules.shared.enums.OperatingSystem;

import java.util.List;

public class AvailableServiceFactory {

    public static AvailableService createAntivirus() {
        AvailableService antivirus = new AvailableService();
        antivirus.setName("Antivirus");
        antivirus.setPricingPolicies(List.of(new AvailableServicePricingPolicy(
                antivirus,
                500L,
                OperatingSystem.WINDOWS
        ),new AvailableServicePricingPolicy(
                antivirus,
                700L,
                OperatingSystem.MAC
        )));
        return antivirus;
    }

    public static AvailableService createCloudberry() {
        AvailableService cloudberry = new AvailableService();
        cloudberry.setName("Cloudberry");
        cloudberry.setPricingPolicies(List.of(new AvailableServicePricingPolicy(
                cloudberry,
                300L,
                OperatingSystem.ALL
        )));
        return cloudberry;
    }

    public static AvailableService createTeamViewer() {
        AvailableService teamViewer = new AvailableService();
        teamViewer.setName("TeamViewer");
        teamViewer.setPricingPolicies(List.of(new AvailableServicePricingPolicy(
                teamViewer,
                100L,
                OperatingSystem.ALL
        )));
        return teamViewer;
    }
}
