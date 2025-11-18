package com.example.devices.service;

import com.example.devices.model.CreateDeviceRequest;
import com.example.devices.model.Device;
import com.example.devices.repository.DeviceEntity;
import com.example.devices.repository.DeviceRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeviceManagementService {
    private DeviceRepository deviceRepository;
    private ModelMapper modelMapper;

    public Device createDevice(CreateDeviceRequest device) {
        return modelMapper.map(deviceRepository.save(modelMapper.map(device, DeviceEntity.class)), Device.class);
    }
}
