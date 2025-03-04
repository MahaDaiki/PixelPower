package com.api.PixelPower.service.serviceInt;

import com.api.PixelPower.dto.response.GameComparisonResponseDTO;

import java.util.List;

public interface GameComparisonServiceInt {
    GameComparisonResponseDTO compareGameWithUserConfig(int appId, Long userId);
    String calculateEstimatedFps(int gpuScore, int cpuScore, String quality);
    List<GameComparisonResponseDTO> getAllGameComparisons();
    List<GameComparisonResponseDTO> getGameComparisonsByAuthenticatedUser();

}
