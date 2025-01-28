package com.api.PixelPower.dto;

import lombok.Data;

@Data
public class UpgradeSuggestionDTO {
    private Long id;
    private String limitingComponent;
    private String suggestedUpgrade;
    private Long configurationId;
}
