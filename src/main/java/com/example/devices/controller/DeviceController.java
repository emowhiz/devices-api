package com.example.devices.controller;

import com.example.devices.model.CreateDeviceRequest;
import com.example.devices.model.Device;
import com.example.devices.service.DeviceManagementService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("v1/devices")
public class DeviceController {
    private final DeviceManagementService deviceManagementService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody CreateDeviceRequest device) {
        return ResponseEntity.ok(deviceManagementService.createDevice(modelMapper.map(device, Device.class)));
    }
}
