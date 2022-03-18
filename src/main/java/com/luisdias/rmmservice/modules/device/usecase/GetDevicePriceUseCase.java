package com.luisdias.rmmservice.modules.device.usecase;

import org.springframework.stereotype.Service;

@Service
public class GetDevicePriceUseCase {

    private static final Long DEVICE_PRICE_IN_CENTS = 400L;

    public Long getPrice() {
        return DEVICE_PRICE_IN_CENTS;
    }
}
