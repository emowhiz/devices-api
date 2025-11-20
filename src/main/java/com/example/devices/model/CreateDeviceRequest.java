package com.example.devices.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateDeviceRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String brand;
    @NotNull
    private DeviceState state;
}
