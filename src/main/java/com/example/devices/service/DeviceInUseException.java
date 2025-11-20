package com.example.devices.service;

public class DeviceInUseException extends RuntimeException {

    public DeviceInUseException(String message) {
        super(message);
    }
}
