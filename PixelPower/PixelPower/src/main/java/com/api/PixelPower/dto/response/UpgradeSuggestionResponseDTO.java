package com.api.PixelPower.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpgradeSuggestionResponseDTO {
    private Long id;
    private String limitingComponent;
    private String suggestedUpgrade;
    private Long gameComparisonId;


}
