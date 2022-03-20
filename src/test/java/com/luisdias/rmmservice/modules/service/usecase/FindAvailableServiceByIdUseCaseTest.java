package com.luisdias.rmmservice.modules.service.usecase;

import com.luisdias.rmmservice.modules.service.entity.AvailableService;
import com.luisdias.rmmservice.modules.service.repository.AvailableServiceRepository;
import com.luisdias.rmmservice.modules.shared.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindAvailableServiceByIdUseCaseTest {
    
    FindAvailableServiceByIdUseCase useCase;

    final Long validAvailableServiceId = 100L;
    final Long invalidAvailableServiceId = 101L;

    @BeforeEach
    void setup() {
        AvailableServiceRepository availableServiceRepository = mock(AvailableServiceRepository.class);

        useCase = new FindAvailableServiceByIdUseCase(availableServiceRepository);

        when(availableServiceRepository.findById(eq(validAvailableServiceId)))
                .thenReturn(Optional.of(new AvailableService()));

        when(availableServiceRepository.findById(eq(invalidAvailableServiceId)))
                .thenReturn(Optional.empty());
    }

    @Test
    void shouldThrowExceptionIfResourceNotFound() {
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> useCase.findById(invalidAvailableServiceId));
    }

    @Test
    void shouldReturnAvailableServiceIfExists() {
        assertThat(useCase.findById(validAvailableServiceId))
                .isInstanceOf(AvailableService.class);
    }
}