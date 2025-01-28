package com.api.PixelPower.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GameComparisonDTO {
    private Long id;
    private String gameName;
    private Boolean compatible;
    private String estimatedFpsLow;
    private String estimatedFpsMedium;
    private String estimatedFpsHigh;
    private LocalDateTime checkedAt;
    private Long configurationId;
}
