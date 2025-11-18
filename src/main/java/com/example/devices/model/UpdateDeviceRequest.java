package com.example.devices.model;


import jakarta.annotation.Nonnull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateDeviceRequest {
    @Nonnull
    private Long id;
    private String name;
    private String brand;
    private DeviceState state;
}
