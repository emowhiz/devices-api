package com.example.devices.service;

import com.example.devices.model.Device;
import com.example.devices.model.DevicePage;
import com.example.devices.model.DeviceState;
import com.example.devices.repository.DeviceEntity;
import com.example.devices.repository.DeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        if (!IN_USE.equals(deviceToUpdate.getState())) {
            if (device.getName() != null || device.getState() != null) {
                throw new DeviceInUseException("Trying to update a device that is in use");
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

    public DevicePage fetchAllDevices(int page, int size) {
        var existingPage = deviceRepository.findAll(PageRequest.of(page, size));
        return getDevicePage(existingPage);
    }

    public DevicePage fetchAllDevicesByBrand(String brand, int page, int size) {
        var existingPage = deviceRepository.findAllByBrand(brand, PageRequest.of(page,size));
        return getDevicePage(existingPage);
    }

    public DevicePage fetchAllDevicesByState(DeviceState state, int page, int size) {
        var existingPage = deviceRepository.findAllByState(state, PageRequest.of(page, size));
        return getDevicePage(existingPage);
    }

    public void deleteDevice(Long id) {
        var existingEntity = deviceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Device not found"));
        if (!IN_USE.equals(existingEntity.getState())) {
            deviceRepository.deleteById(id);
        }
        throw new DeviceInUseException("Trying to delete a device is in use");
    }

    private DevicePage getDevicePage(Page<DeviceEntity> existingPage) {
        return DevicePage.builder()
                .items(existingPage.stream()
                        .map(e -> modelMapper.map(e, Device.class))
                        .toList())
                .count(existingPage.getNumberOfElements())
                .totalPages(existingPage.getTotalPages())
                .totalCount(existingPage.getTotalElements())
                .pageNumber(existingPage.getNumber())
                .build();
    }
}
