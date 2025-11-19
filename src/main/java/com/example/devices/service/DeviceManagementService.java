package com.example.devices.service;

import com.example.devices.model.Device;
import com.example.devices.model.DeviceState;
import com.example.devices.repository.DeviceEntity;
import com.example.devices.repository.DeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.devices.model.DeviceState.IN_USE;

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

    public Device updateDevice(Device device) {
        var deviceToUpdate = deviceRepository.findById(device.getId()).orElseThrow(() -> new EntityNotFoundException("Device not found"));
        //TODO add a custom exception handling?
        if (!IN_USE.equals(deviceToUpdate.getState())) {
            if (device.getName() != null) {
                deviceToUpdate.setName(device.getName());
            }
            if (device.getBrand() != null) {
                deviceToUpdate.setBrand(device.getBrand());
            }
        }
        if (device.getState() != null) {
            deviceToUpdate.setState(device.getState());
        }
        var savedEntity = deviceRepository.save(deviceToUpdate);
        return modelMapper.map(savedEntity, Device.class);
    }

    public Device fetchDevice(Long id) {
        var foundEntity = deviceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Device not found"));
        return modelMapper.map(foundEntity, Device.class);
    }
    public List<Device> fetchAllDevices() {
        var existingEntities = deviceRepository.findAll();
        return existingEntities.stream().map(e -> modelMapper.map(e, Device.class)).toList();
    }

    public List<Device> fetchAllDevicesByBrand(String brand) {
        var existingEntities = deviceRepository.findAllByBrand(brand);
        return existingEntities.stream().map(e -> modelMapper.map(e, Device.class)).toList();
    }

    public List<Device> fetchAllDevicesByState(DeviceState state) {
        var existingEntities = deviceRepository.findAllByState(state);
        return existingEntities.stream().map(e -> modelMapper.map(e, Device.class)).toList();
    }
}
