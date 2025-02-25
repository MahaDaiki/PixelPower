package com.api.PixelPower.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GameComparisonDTO {
    private Long id;
    private String gameName;
    private Boolean cpuGompatible;
    private Boolean cpuCompatible;
    private Boolean ramCompatible;
    private Boolean storageCompatible;
    private Boolean osCompatible;
    private Boolean compatible;
    private String estimatedFpsLow;
    private String estimatedFpsMedium;
    private String estimatedFpsHigh;
    private LocalDateTime checkedAt;
    private Long configurationId;
}
