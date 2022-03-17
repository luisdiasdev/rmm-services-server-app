package com.luisdias.rmmservice.modules.service.usecase;

import com.luisdias.rmmservice.modules.service.api.request.CreateAvailableServiceRequest;
import com.luisdias.rmmservice.modules.service.entity.AvailableService;
import com.luisdias.rmmservice.modules.service.entity.AvailableServicePricingPolicy;
import com.luisdias.rmmservice.modules.service.repository.AvailableServicePricingPolicyRepository;
import com.luisdias.rmmservice.modules.service.repository.AvailableServiceRepository;
import com.luisdias.rmmservice.modules.shared.OperatingSystem;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
public class CreateAvailableServiceUseCase {

    private final AvailableServiceRepository availableServiceRepository;
    private final AvailableServicePricingPolicyRepository availableServicePricingPolicyRepository;

    public CreateAvailableServiceUseCase(AvailableServiceRepository availableServiceRepository, AvailableServicePricingPolicyRepository availableServicePricingPolicyRepository) {
        this.availableServiceRepository = availableServiceRepository;
        this.availableServicePricingPolicyRepository = availableServicePricingPolicyRepository;
    }

    @Transactional
    public void create(CreateAvailableServiceRequest request) {
        AvailableService availableService = new AvailableService();
        availableService.setDescription(request.getDescription());
        availableService.setName(request.getName());
        availableService.getPricingPolicies().addAll(
                request.getPricingPolicies()
                        .stream()
                        .map(p -> new AvailableServicePricingPolicy(
                                availableService,
                                p.getPrice(),
                                OperatingSystem.valueOf(p.getOs())))
                        .collect(Collectors.toList())
        );
        availableServiceRepository.save(availableService);
        availableServicePricingPolicyRepository.saveAll(availableService.getPricingPolicies());
    }
}
