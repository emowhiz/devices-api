package com.example.devices.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateDeviceRequest {
    private String name;
    private String brand;
    private DeviceState state;
}
