package com.api.PixelPower.dto.response;


import lombok.Data;

@Data
public class GameRequirementsResponseDTO {
    private String gameName;
    private String minCpu;
    private String maxCpu;
    private String minGpu;
    private String maxGpu;
    private String ram;
    private String os;
    private String storage;
}
