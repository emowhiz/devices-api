package com.example.devices.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    private Long id;
    private String name;
    private String brand;
    private DeviceState state;
    private Instant createdAt;
}
