package com.api.PixelPower.service.serviceInt;

import com.api.PixelPower.dto.response.UpgradeSuggestionResponseDTO;

import java.util.List;

public interface UpgradeSuggestionServiceInt {
    List<UpgradeSuggestionResponseDTO> getAllUpgradeSuggestions();
    List<UpgradeSuggestionResponseDTO> getUpgradeSuggestionsByComparisonId(Long comparisonId);
    List<UpgradeSuggestionResponseDTO> generateUpgradeSuggestions(Long comparisonId);
}
