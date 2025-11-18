package com.example.devices.controller;

import com.example.devices.model.CreateDeviceRequest;
import com.example.devices.model.Device;
import com.example.devices.model.UpdateDeviceRequest;
import com.example.devices.service.DeviceManagementService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("v1/devices")
public class DeviceController {
    private final DeviceManagementService deviceManagementService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody CreateDeviceRequest createRequest) {
        return ResponseEntity.ok(deviceManagementService.createDevice(modelMapper.map(createRequest, Device.class)));
    }
    @PutMapping
    public ResponseEntity<Device> updateDevice(@Valid @RequestBody UpdateDeviceRequest updateRequest) {
        return ResponseEntity.ok(deviceManagementService.updateDevice(modelMapper.map(updateRequest, Device.class)));
    }
    @GetMapping
    public ResponseEntity<Device> getDevice(@PathVariable Long id) {
        return ResponseEntity.ok(deviceManagementService.getDevice(id));
    }
}
