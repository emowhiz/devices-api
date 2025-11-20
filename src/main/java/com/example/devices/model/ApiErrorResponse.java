package com.example.devices.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class ApiErrorResponse {
    private String statusCode;
    private String message;
    private Map<String, String> errors;
    private LocalDateTime timestamp;

}
