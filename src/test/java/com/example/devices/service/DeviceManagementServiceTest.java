package com.example.devices.service;

import com.example.devices.config.BeanConfig;
import com.example.devices.model.Device;
import com.example.devices.model.DeviceState;
import com.example.devices.repository.DeviceEntity;
import com.example.devices.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

}