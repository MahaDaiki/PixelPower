package com.api.PixelPower.dto.request;

import lombok.Data;

@Data
public class GameRequirementsRequestDTO {
    private String pcRequirementsMinimum;
    private String pcRequirementsRecommended;
    private String macRequirementsMinimum;
    private String macRequirementsRecommended;
    private String linuxRequirementsMinimum;
    private String linuxRequirementsRecommended;
}
