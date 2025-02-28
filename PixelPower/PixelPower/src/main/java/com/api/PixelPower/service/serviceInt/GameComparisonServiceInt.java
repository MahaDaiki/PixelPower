package com.api.PixelPower.service.serviceInt;

import com.api.PixelPower.dto.GameComparisonDTO;

public interface GameComparisonServiceInt {
    GameComparisonDTO compareGameWithUserConfig(int appId, Long userId);
    String calculateEstimatedFps(int gpuScore, int cpuScore, String quality);

}
