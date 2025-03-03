package com.api.PixelPower.service.serviceInt;

import com.api.PixelPower.dto.UpgradeSuggestionDTO;

import java.util.List;

public interface UpgradeSuggestionServiceInt {
    List<UpgradeSuggestionDTO> getAllUpgradeSuggestions();
    List<UpgradeSuggestionDTO> getUpgradeSuggestionsByComparisonId(Long comparisonId);
    void generateUpgradeSuggestions(Long comparisonId);
}
