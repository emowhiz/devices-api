package com.example.devices.service;

import com.example.devices.model.CreateDeviceRequest;
import com.example.devices.model.Device;
import com.example.devices.repository.DeviceEntity;
import com.example.devices.repository.DeviceRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeviceManagementService {
    private final DeviceRepository deviceRepository;
    private final ModelMapper modelMapper;

    public Device createDevice(Device device) {
        var mappedEntity = modelMapper.map(device, DeviceEntity.class);
        var savedEntity = deviceRepository.save(mappedEntity);
        return modelMapper.map(savedEntity, Device.class);
    }
}
