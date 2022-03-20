package com.luisdias.rmmservice.modules.customer.usecase;

import com.luisdias.rmmservice.modules.customer.api.response.CustomerBillResponse;
import com.luisdias.rmmservice.modules.device.api.response.DeviceResponse;
import com.luisdias.rmmservice.modules.device.usecase.FindAllDevicesUseCase;
import com.luisdias.rmmservice.modules.device.usecase.GetDevicePriceUseCase;
import com.luisdias.rmmservice.modules.service.entity.AvailableServicePricingPolicy;
import com.luisdias.rmmservice.modules.service.entity.CustomerService;
import com.luisdias.rmmservice.modules.service.usecase.FindAllCustomerServicesUseCase;
import com.luisdias.rmmservice.modules.shared.enums.OperatingSystem;
import com.luisdias.rmmservice.modules.shared.exception.UnauthorizedException;
import com.luisdias.rmmservice.modules.shared.service.AuthenticationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CalculateCustomerBillUseCase {

    private final AuthenticationService authenticationService;
    private final FindAllDevicesUseCase findAllDevicesUseCase;
    private final FindAllCustomerServicesUseCase findAllCustomerServicesUseCase;
    private final GetDevicePriceUseCase getDevicePriceUseCase;

    public CalculateCustomerBillUseCase(AuthenticationService authenticationService,
                                        FindAllDevicesUseCase findAllDevicesUseCase,
                                        FindAllCustomerServicesUseCase findAllCustomerServicesUseCase,
                                        GetDevicePriceUseCase getDevicePriceUseCase) {
        this.authenticationService = authenticationService;
        this.findAllDevicesUseCase = findAllDevicesUseCase;
        this.findAllCustomerServicesUseCase = findAllCustomerServicesUseCase;
        this.getDevicePriceUseCase = getDevicePriceUseCase;
    }

    public CustomerBillResponse calculateBill(Long customerId) {
        if (!Objects.equals(authenticationService.getAuthenticatedCustomerId(), customerId)) {
            throw new UnauthorizedException();
        }
        var devices = findAllDevicesUseCase.findAll();
        var customerServices = findAllCustomerServicesUseCase.findAll();
        var pricesByOperatingSystem = calculatePricesByOperatingSystem(customerServices);
        var operatingSystemSpecificCost = calculateOperatingSystemSpecificCost(devices, pricesByOperatingSystem);
        var remainingCost = calculateOperatingSystemIndependentCost(
                (long) devices.size(),
                getDevicePriceUseCase.getPrice(),
                pricesByOperatingSystem.getOrDefault(OperatingSystem.ALL, 0L));

        return new CustomerBillResponse(operatingSystemSpecificCost + remainingCost);
    }

    private Long calculateOperatingSystemIndependentCost(Long deviceCount, Long pricePerDevice, Long operatingSystemIndependentPrice) {
        return (deviceCount * pricePerDevice) + (deviceCount * operatingSystemIndependentPrice);
    }

    private Long calculateOperatingSystemSpecificCost(List<DeviceResponse> devices,
                                                      Map<OperatingSystem, Long> pricesByOperatingSystem) {
        return devices.stream()
                .map(DeviceResponse::getOperatingSystem)
                .reduce(0L, (accumulatedPrice, operatingSystem) -> {
                    var price = pricesByOperatingSystem.get(operatingSystem);
                    return accumulatedPrice + price;
                }, Long::sum);
    }

    private Map<OperatingSystem, Long> calculatePricesByOperatingSystem(List<CustomerService> customerServices) {
        return customerServices.stream()
                .map(CustomerService::getService)
                .flatMap(service ->
                        service.getPricingPolicies()
                                .stream()
                                .collect(Collectors.toMap(
                                        AvailableServicePricingPolicy::getOperatingSystem,
                                        AvailableServicePricingPolicy::getPrice,
                                        Long::sum
                                )).entrySet().stream()
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Long::sum));
    }
}
