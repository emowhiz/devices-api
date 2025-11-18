package com.example.devices.controller;

import com.example.devices.model.CreateDeviceRequest;
import com.example.devices.model.Device;
import com.example.devices.service.DeviceManagementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("v1/devices")
public class DeviceController {
    private DeviceManagementService deviceManagementService;

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody CreateDeviceRequest device) {
        return ResponseEntity.ok(deviceManagementService.createDevice(device));
    }
}
