package com.luisdias.rmmservice.modules.device.usecase;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GetDevicePriceUseCaseTest {

    @Test
    void shouldReturn400CentsAsDeviceCost() {
        GetDevicePriceUseCase useCase = new GetDevicePriceUseCase();
        assertThat(useCase.getPrice()).isEqualTo(400L);
    }

}