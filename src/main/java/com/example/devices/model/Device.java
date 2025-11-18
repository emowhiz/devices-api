package com.example.devices.model;


import lombok.Data;

import java.time.Instant;

@Data
public class Device {
    private Long id;
    private String name;
    private String brand;
    private DeviceState state;
    private Instant createdAt;
}
