package com.example.devices.controller;

import com.example.devices.model.ApiErrorResponse;
import com.example.devices.model.ErrorCode;
import com.example.devices.service.DeviceInUseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(EntityNotFoundException ex) {
        var errorResponse = new ApiErrorResponse(
                ErrorCode.ERR_CODE_NOT_FOUND,
                ex.getMessage(),
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(DeviceInUseException.class)
    public ResponseEntity<ApiErrorResponse> handleDeviceInUseException(DeviceInUseException ex) {
        var errorResponse = new ApiErrorResponse(
                ErrorCode.ERR_CODE_INVALID_REQUEST,
                ex.getMessage(),
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGlobalException(Exception ex) {
        var errorResponse = new ApiErrorResponse(
                ErrorCode.ERR_CODE_GENERIC_INTERNAL_SRV_ERR,
                "An unexpected error occurred",
                null,
                LocalDateTime.now()
        );

        log.error("Exception occurred [{}]", errorResponse, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

