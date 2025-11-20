package com.example.devices.controller;

import com.example.devices.model.*;
import com.example.devices.service.DeviceManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("v1/devices")
public class DeviceController {
    private final DeviceManagementService deviceManagementService;
    private final ModelMapper modelMapper;

    @PostMapping
    @Operation(
            summary = "Create a new device",
            description = "Creates a new device in the system and returns the created device with generated ID and timestamps"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Device successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Device.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body or validation errors"
            )
    })
    public ResponseEntity<Device> createDevice(@Valid @RequestBody CreateDeviceRequest createRequest) {
        log.info("Received request to create a device with name: [{}] and brand : [{}]", createRequest.getName(), createRequest.getBrand());
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceManagementService.createDevice(modelMapper.map(createRequest, Device.class)));
    }

    @PutMapping
    @Operation(
            summary = "Update an existing device",
            description = "Update an existing device "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Device successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Device.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body or validation errors"
            )
    })
    public ResponseEntity<Device> updateDevice(@Valid @RequestBody UpdateDeviceRequest updateRequest) {
        log.info("Received request to update a device with ID: [{}] ", updateRequest.getId());
        return ResponseEntity.ok(deviceManagementService.updateDevice(modelMapper.map(updateRequest, Device.class)));
    }

    @Operation(
            summary = "Fetch an existing device",
            description = "Fetch an existing device "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Device successfully fetched",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Device.class)
                    )
            )
    })
    @Parameter(
            name = "id",
            description = "Unique identifier of the device",
            required = true,
            example = "1",
            schema = @Schema(type = "integer", format = "int64")
    )
    @GetMapping("/{id}")
    public ResponseEntity<Device> fetchDevice(@PathVariable Long id) {
        log.info("Received request to fetch a device with ID: [{}] ", id);
        return ResponseEntity.ok(deviceManagementService.fetchDevice(id));
    }

    @Operation(
            summary = "Fetch all devices with pagination",
            description = "Retrieves a paginated list of all devices in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved paginated list of devices",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DevicePage.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination parameters"
            )
    })
    @Parameter(
            name = "page",
            description = "Page number (zero-based index)",
            example = "0",
            schema = @Schema(type = "integer", defaultValue = "0", minimum = "0")
    )
    @Parameter(
            name = "size",
            description = "Number of devices per page",
            example = "10",
            schema = @Schema(type = "integer", defaultValue = "10", minimum = "1", maximum = "100")
    )
    @GetMapping
    public ResponseEntity<DevicePage> fetchAllDevices(@RequestParam(defaultValue = "0")
                                                      @Min(value = 0, message = "Page number must be greater than or equal to 0")
                                                      Integer page,
                                                      @RequestParam(defaultValue = "10")
                                                      @Min(value = 1, message = "Page size must be at least 1")
                                                      @Max(value = 100, message = "Page size must not exceed 100")
                                                      Integer size) {
        log.info("Received request to fetch all devices with page [{}] and size [{}]", page, size);
        return ResponseEntity.ok(deviceManagementService.fetchAllDevices(page, size));
    }

    @Operation(
            summary = "Fetch all devices filtered by brand with pagination",
            description = "Retrieves a paginated list of devices in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved paginated list of devices",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DevicePage.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination parameters"
            )
    })
    @Parameter(
            name = "brand",
            description = "Brand name to filter devices",
            required = true,
            example = "Sample Brand",
            schema = @Schema(type = "string")
    )
    @Parameter(
            name = "page",
            description = "Page number (zero-based index)",
            example = "0",
            schema = @Schema(type = "integer", defaultValue = "0", minimum = "0")
    )
    @Parameter(
            name = "size",
            description = "Number of devices per page",
            example = "10",
            schema = @Schema(type = "integer", defaultValue = "10", minimum = "1", maximum = "100")
    )
    @GetMapping("/brand/{brand}")
    public ResponseEntity<DevicePage> fetchAllDevicesByBrand(@PathVariable String brand,
                                                             @RequestParam(defaultValue = "0")
                                                             @Min(value = 0, message = "Page number must be greater than or equal to 0")
                                                             Integer page,
                                                             @RequestParam(defaultValue = "10")
                                                             @Min(value = 1, message = "Page size must be at least 1")
                                                             @Max(value = 100, message = "Page size must not exceed 100")
                                                             Integer size) {
        log.info("Received request to fetch all devices with brand [{}] page [{}] and size [{}]", brand, page, size);
        return ResponseEntity.ok(deviceManagementService.fetchAllDevicesByBrand(brand, page, size));
    }

    @Operation(
            summary = "Fetch all devices filtered by brand with pagination",
            description = "Retrieves a paginated list of devices in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved paginated list of devices",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DevicePage.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination parameters"
            )
    })
    @Parameter(
            name = "state",
            description = "Device state to filter devices",
            required = true,
            example = "AVAILABLE",
            schema = @Schema(implementation = DeviceState.class)
    )
    @Parameter(
            name = "page",
            description = "Page number (zero-based index)",
            example = "0",
            schema = @Schema(type = "integer", defaultValue = "0", minimum = "0")
    )
    @Parameter(
            name = "size",
            description = "Number of devices per page",
            example = "10",
            schema = @Schema(type = "integer", defaultValue = "10", minimum = "1", maximum = "100")
    )
    @GetMapping("/state/{state}")
    public ResponseEntity<DevicePage> fetchAllDevicesByState(@PathVariable DeviceState state,
                                                             @RequestParam(defaultValue = "0")
                                                             @Min(value = 0, message = "Page number must be greater than or equal to 0")
                                                             Integer page,
                                                             @RequestParam(defaultValue = "10")
                                                             @Min(value = 1, message = "Page size must be at least 1")
                                                             @Max(value = 100, message = "Page size must not exceed 100")
                                                             Integer size) {
        log.info("Received request to fetch all devices with state [{}] page [{}] and size [{}]", state, page, size);
        return ResponseEntity.ok(deviceManagementService.fetchAllDevicesByState(state, page, size));
    }

    @Operation(
            summary = "Delete device by ID",
            description = "Deletes a specific device from the system by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Device successfully deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Device not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid device ID format"
            )
    })
    @Parameter(
            name = "id",
            description = "Unique identifier of the device to delete",
            required = true,
            example = "1",
            schema = @Schema(type = "integer", format = "int64")
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable
                                             @Min(value = 1, message = "Device ID must be greater than 0")
                                             Long id) {
        log.info("Received request to delete a device with ID: [{}] ", id);
        deviceManagementService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
}
