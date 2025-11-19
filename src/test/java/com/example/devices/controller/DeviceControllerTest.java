package com.example.devices.controller;

import com.example.devices.model.CreateDeviceRequest;
import com.example.devices.model.Device;
import com.example.devices.model.DeviceState;
import com.example.devices.model.UpdateDeviceRequest;
import com.example.devices.repository.DeviceEntity;
import com.example.devices.repository.DeviceRepository;
import com.example.devices.service.DeviceManagementService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class DeviceControllerTest {


    @Autowired
    private DeviceRepository deviceRepository;

    @LocalServerPort
    private Integer port;

    @Autowired
    TestRestTemplate restTemplate;


    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));


    @BeforeAll
    static void beforeAll() {
        postgresContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgresContainer.stop();
    }

    @BeforeEach
    void setUp() {
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:" + port));
    }

    @Test
    void shouldCreateDevice() {
        var device = CreateDeviceRequest.builder()
                .name("Test Device")
                .brand("Test brand")
                .state(DeviceState.AVAILABLE)
                .build();

        var response = restTemplate.exchange("/v1/devices", HttpMethod.POST, new HttpEntity<>(device), Device.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertNotNull(responseBody.getId());
        assertEquals(device.getName(), responseBody.getName());
        assertEquals(device.getBrand(), responseBody.getBrand());
        assertEquals(device.getState(), responseBody.getState());

    }
    @Test
    void shouldUpdateDevice() {
        var device = DeviceEntity.builder()
                .name("Test Device")
                .brand("Test brand")
                .state(DeviceState.AVAILABLE)
                .build();

        var saved = deviceRepository.save(device);

        var updateDeviceRequest = UpdateDeviceRequest.builder()
                .id(saved.getId())
                .name("New Name")
                .brand("New brand")
                .state(DeviceState.INACTIVE)
                .build();

        var response = restTemplate.exchange("/v1/devices", HttpMethod.PUT, new HttpEntity<>(updateDeviceRequest), Device.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(updateDeviceRequest.getId(), responseBody.getId());
        assertEquals(updateDeviceRequest.getName(), responseBody.getName());
        assertEquals(updateDeviceRequest.getBrand(), responseBody.getBrand());
        assertEquals(updateDeviceRequest.getState(), responseBody.getState());

    }


    @Test
    void shouldNotUpdateNameAndBrandInUseDevice() {
        var device = DeviceEntity.builder()
                .name("Test Device")
                .brand("Test brand")
                .state(DeviceState.IN_USE)
                .build();

        var saved = deviceRepository.save(device);

        var updateDeviceRequest = UpdateDeviceRequest.builder()
                .id(saved.getId())
                .name("New Name")
                .brand("New brand")
                .state(DeviceState.INACTIVE)
                .build();

        var response = restTemplate.exchange("/v1/devices", HttpMethod.PUT, new HttpEntity<>(updateDeviceRequest), Device.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(updateDeviceRequest.getId(), responseBody.getId());
        assertEquals(device.getName(), responseBody.getName());
        assertEquals(device.getBrand(), responseBody.getBrand());
        assertEquals(updateDeviceRequest.getState(), responseBody.getState());

    }

    @Test
    void shouldFetchExistingDevice() {
        var device = DeviceEntity.builder()
                .name("Test Device")
                .brand("Test brand")
                .state(DeviceState.AVAILABLE)
                .build();

        var saved = deviceRepository.save(device);

        var response = restTemplate.getForEntity("/v1/devices/"+saved.getId(), Device.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        var responseBody = response.getBody();
        assertEquals(device.getId(), responseBody.getId());
        assertEquals(device.getName(), responseBody.getName());
        assertEquals(device.getName(), responseBody.getName());
        assertEquals(device.getBrand(), responseBody.getBrand());
        assertEquals(device.getState(), responseBody.getState());
    }


}