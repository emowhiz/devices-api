package com.example.devices.model;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateDeviceRequest {
    @NotNull
    private Long id;
    private String name;
    private String brand;
    private DeviceState state;
}
