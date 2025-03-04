package com.api.PixelPower.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GameComparisonResponseDTO {
    private Long id;
    private String gameName;
    private Boolean gpuCompatible;
    private Boolean cpuCompatible;
    private Boolean ramCompatible;
    private Boolean storageCompatible;
    private Boolean compatible;
    private String estimatedFpsLow;
    private String estimatedFpsMedium;
    private String estimatedFpsHigh;
    private LocalDateTime checkedAt;
    private Long configurationId;
}
