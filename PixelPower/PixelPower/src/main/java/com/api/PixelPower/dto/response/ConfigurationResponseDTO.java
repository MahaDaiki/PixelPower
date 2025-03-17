package com.api.PixelPower.dto.response;

import com.api.PixelPower.entity.ConfigStatus;
import com.api.PixelPower.entity.OperatingSystem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigurationResponseDTO {
    private Long id;
    private String name;
    private String cpu;
    private String gpu;
    private String ram;
    private String storage;
    private OperatingSystem os;
    private ConfigStatus status;
    private Long userId;
    private List<GameComparisonResponseDTO> gameComparisons;
    private List<UpgradeSuggestionResponseDTO> upgradeSuggestions;
}
