package com.api.PixelPower.dto;


import com.api.PixelPower.entity.ConfigStatus;
import com.api.PixelPower.entity.OperatingSystem;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConfigurationDTO {
    private Long id;
    private String name;
    private String cpu;
    private String gpu;
    private String ram;
    private String storage;
    private OperatingSystem os;
    private ConfigStatus status;
    private LocalDateTime createdAt;
    private Long userId;
}
