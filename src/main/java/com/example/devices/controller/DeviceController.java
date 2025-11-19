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

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<Device> fetchDevice(@PathVariable Long id) {
        return ResponseEntity.ok(deviceManagementService.fetchDevice(id));
    }

    @GetMapping
    public ResponseEntity<List<Device>> fetchAllDevices() {
        //TODO paginate
        return ResponseEntity.ok(deviceManagementService.fetchAllDevices());
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Device>> fetchAllDevicesByBrand(@PathVariable String brand) {
        //TODO paginate
        return ResponseEntity.ok(deviceManagementService.fetchAllDevicesByBrand(brand));
    }
}
