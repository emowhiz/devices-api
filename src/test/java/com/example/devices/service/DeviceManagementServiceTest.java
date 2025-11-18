package com.example.devices.service;

import com.example.devices.config.BeanConfig;
import com.example.devices.model.Device;
import com.example.devices.model.DeviceState;
import com.example.devices.repository.DeviceEntity;
import com.example.devices.repository.DeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {DeviceManagementService.class, BeanConfig.class})
class DeviceManagementServiceTest {

    @MockitoBean
    DeviceRepository deviceRepository;

    @Autowired
    DeviceManagementService deviceManagementService;


    @Test
    void shouldCreateDeviceAndReturnIt() {
        var request = Device.builder()
                .name("Test Device")
                .brand("Test Brand")
                .state(DeviceState.AVAILABLE)
                .build();
        var savedEntity = DeviceEntity.builder()
                .id(1L)
                .name("Test Device")
                .brand("Test Brand")
                .state(DeviceState.AVAILABLE)
                .createdAt(Instant.now())
                .build();
        when(deviceRepository.save(any())).thenReturn(savedEntity);

        var actualResponse = deviceManagementService.createDevice(request);

        assertNotNull(actualResponse);
        assertEquals(savedEntity.getName(), actualResponse.getName());
        assertEquals(savedEntity.getId(), actualResponse.getId());
        assertEquals(savedEntity.getBrand(), actualResponse.getBrand());
        assertEquals(savedEntity.getState(), actualResponse.getState());
        assertEquals(savedEntity.getCreatedAt(), actualResponse.getCreatedAt());
    }

    @Test
    void shouldThrowAnExceptionOnNonExistingDeviceOnUpdate() {
        var request = Device.builder()
                .id(1L)
                .name("New Device Name")
                .build();

        when(deviceRepository.findById(any())).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> deviceManagementService.updateDevice(request));
    }

    @Test
    void shouldThrowAnExceptionOnNonExistingDeviceOnFetch() {
        when(deviceRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> deviceManagementService.getDevice(1L));
    }



    @Test
    void shouldReturnFoundDevice() {
        var existing = DeviceEntity.builder()
                .id(1L)
                .name("Test Device")
                .brand("Test Brand")
                .state(DeviceState.AVAILABLE)
                .createdAt(Instant.now())
                .build();
        when(deviceRepository.findById(any())).thenReturn(Optional.of(existing));

        var actualResponse = deviceManagementService.getDevice(1L);

        assertNotNull(actualResponse);
        assertEquals(existing.getName(), actualResponse.getName());
        assertEquals(existing.getId(), actualResponse.getId());
        assertEquals(existing.getBrand(), actualResponse.getBrand());
        assertEquals(existing.getState(), actualResponse.getState());
        assertEquals(existing.getCreatedAt(), actualResponse.getCreatedAt());
    }

}