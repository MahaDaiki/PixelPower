package com.api.PixelPower.service.serviceImpl;

import com.api.PixelPower.dto.GameComparisonDTO;
import com.api.PixelPower.dto.response.ConfigurationResponseDTO;
import com.api.PixelPower.dto.response.GameRequirementsResponseDTO;
import com.api.PixelPower.entity.GameComparison;
import com.api.PixelPower.mapper.GameComparisonMapper;
import com.api.PixelPower.repository.GameComparisonRepository;
import com.api.PixelPower.service.serviceInt.BenchmarkCalculatorServiceInt;
import com.api.PixelPower.service.serviceInt.ConfigurationServiceInt;
import com.api.PixelPower.service.serviceInt.GameComparisonServiceInt;
import com.api.PixelPower.service.serviceInt.GameRequirementServiceInt;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GameComparisonServiceImpl implements GameComparisonServiceInt {
    private final BenchmarkCalculatorServiceInt benchmarkCalculator;
    private final GameRequirementServiceInt gameRequirementService;
    private final ConfigurationServiceInt configurationService;
    private final GameComparisonRepository gameComparisonRepository;
    private final GameComparisonMapper gameComparisonMapper;


    private int extractNumber(String value) {
        return value.replaceAll("[^0-9]", "").isEmpty() ? 0 : Integer.parseInt(value.replaceAll("[^0-9]", ""));
    }


    @Override
    public GameComparisonDTO compareGameWithUserConfig(int appId, Long userId) {
        List<ConfigurationResponseDTO> userConfigs = configurationService.getConfigurationsByAuthenticatedUser();
        if (userConfigs.isEmpty()) throw new RuntimeException("Aucune configuration utilisateur trouvée !");
        ConfigurationResponseDTO userConfig = userConfigs.get(0);

        // Get game requirements
        GameRequirementsResponseDTO gameRequirements = gameRequirementService.parseRequirements(appId, userId);

        // Calculate CPU and GPU scores for user configuration
        int userCpuScore = benchmarkCalculator.calculateCpuScore(userConfig.getCpu());
        int userGpuScore = benchmarkCalculator.calculateGpuScore(userConfig.getGpu());

        // Get game requirements for CPU and GPU scores
        int minCpuScore = gameRequirements.getMinCpu().equals("N/A") ? 0 : benchmarkCalculator.calculateCpuScore(gameRequirements.getMinCpu());
        int recCpuScore = gameRequirements.getMaxCpu().equals("N/A") ? minCpuScore * 2 : benchmarkCalculator.calculateCpuScore(gameRequirements.getMaxCpu());
        int minGpuScore = gameRequirements.getMinGpu().equals("N/A") ? 0 : benchmarkCalculator.calculateGpuScore(gameRequirements.getMinGpu());
        int recGpuScore = gameRequirements.getMaxGpu().equals("N/A") ? minGpuScore * 2 : benchmarkCalculator.calculateGpuScore(gameRequirements.getMaxGpu());

        // IMPORTANT CHANGE: For compatibility, we only care if user hardware meets minimum requirements
        boolean cpuCompatible = userCpuScore >= minCpuScore;
        boolean gpuCompatible = userGpuScore >= minGpuScore;


        boolean ramCompatible = extractNumber(userConfig.getRam()) >= extractNumber(gameRequirements.getRam());
        boolean storageCompatible = extractNumber(userConfig.getStorage()) >= extractNumber(gameRequirements.getStorage());



        // Final compatibility check
        boolean compatible = cpuCompatible && gpuCompatible && ramCompatible && storageCompatible;

        // Calculate estimated FPS for different quality levels
        String fpsLow = calculateEstimatedFps(userGpuScore, userCpuScore, "low");
        String fpsMedium = calculateEstimatedFps(userGpuScore, userCpuScore, "medium");
        String fpsHigh = calculateEstimatedFps(userGpuScore, userCpuScore, "high");

        GameComparison gameComparison = GameComparison.builder()
                .gameName(gameRequirements.getGameName())
                .gpuCompatible(gpuCompatible)
                .cpuCompatible(cpuCompatible)
                .ramCompatible(ramCompatible)
                .storageCompatible(storageCompatible)
                .compatible(compatible)
                .estimatedFpsLow(fpsLow)
                .estimatedFpsMedium(fpsMedium)
                .estimatedFpsHigh(fpsHigh)
                .configuration(configurationService.getUserPrimaryConfiguration(userId))
                .build();

        gameComparisonRepository.save(gameComparison);

        return gameComparisonMapper.toDTO(gameComparison);
    }

    @Override

    public String calculateEstimatedFps(int gpuScore, int cpuScore, String quality) {
        // Base difficulty scaling adjusted dynamically using GPU score
        double gameDifficultyFactor = Math.max(180, gpuScore / 80.0);

        // Weighted Performance Score
        double weightedPerformance = (gpuScore * 0.7 + cpuScore * 0.3) / gameDifficultyFactor;

        // Quality multipliers
        double qualityMultiplier = switch (quality.toLowerCase()) {
            case "low" -> 1.8;
            case "medium" -> 1.2;
            case "high" -> 0.8;
            default -> 1.0; // Default case (if input is invalid)
        };

        // Final FPS Calculation
        int estimatedFps = (int) Math.round(weightedPerformance * qualityMultiplier);

        // Ensure FPS stays within a reasonable range
        estimatedFps = Math.max(15, Math.min(estimatedFps, 240));

        return estimatedFps + " FPS";
    }

}

