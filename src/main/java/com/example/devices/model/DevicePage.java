package com.example.devices.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DevicePage {
    private List<Device> items;
    private Long totalCount;
    private Integer totalPages;
    private Integer pageNumber;
    private Integer count;
}
