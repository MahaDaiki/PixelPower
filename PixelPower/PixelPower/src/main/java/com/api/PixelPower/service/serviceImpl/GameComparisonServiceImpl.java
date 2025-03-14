package com.api.PixelPower.service.serviceImpl;

import com.api.PixelPower.dto.response.GameComparisonResponseDTO;
import com.api.PixelPower.dto.response.ConfigurationResponseDTO;
import com.api.PixelPower.dto.response.GameRequirementsResponseDTO;
import com.api.PixelPower.entity.GameComparison;
import com.api.PixelPower.entity.User;
import com.api.PixelPower.exception.ResourceNotFoundException;
import com.api.PixelPower.mapper.GameComparisonMapper;
import com.api.PixelPower.repository.GameComparisonRepository;
import com.api.PixelPower.repository.UserRepository;
import com.api.PixelPower.service.serviceInt.BenchmarkCalculatorServiceInt;
import com.api.PixelPower.service.serviceInt.ConfigurationServiceInt;
import com.api.PixelPower.service.serviceInt.GameComparisonServiceInt;
import com.api.PixelPower.service.serviceInt.GameRequirementServiceInt;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GameComparisonServiceImpl implements GameComparisonServiceInt {
    private final BenchmarkCalculatorServiceInt benchmarkCalculator;
    private final GameRequirementServiceInt gameRequirementService;
    private final ConfigurationServiceInt configurationService;
    private final GameComparisonRepository gameComparisonRepository;
    private final GameComparisonMapper gameComparisonMapper;
    private final UserRepository userRepository;


    private int extractNumber(String value) {
        return value.replaceAll("[^0-9]", "").isEmpty() ? 0 : Integer.parseInt(value.replaceAll("[^0-9]", ""));
    }


    @Override
    public GameComparisonResponseDTO compareGameWithUserConfig(int appId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Long userId = user.getId();

        List<ConfigurationResponseDTO> userConfigs = configurationService.getConfigurationsByAuthenticatedUser();
        if (userConfigs.isEmpty()) throw new RuntimeException("Aucune configuration utilisateur trouvée !");
        ConfigurationResponseDTO userConfig = userConfigs.get(0);

        GameRequirementsResponseDTO gameRequirements = gameRequirementService.parseRequirements(appId, userId);

        int userCpuScore = benchmarkCalculator.calculateCpuScore(userConfig.getCpu());
        int userGpuScore = benchmarkCalculator.calculateGpuScore(userConfig.getGpu());

        int minCpuScore = (gameRequirements == null || gameRequirements.getMinCpu() == null || gameRequirements.getMinCpu().equals("N/A"))
                ? 0
                : benchmarkCalculator.calculateCpuScore(gameRequirements.getMinCpu());

        int minGpuScore = (gameRequirements == null || gameRequirements.getMinGpu() == null || gameRequirements.getMinGpu().equals("N/A"))
                ? 0
                : benchmarkCalculator.calculateGpuScore(gameRequirements.getMinGpu());

        boolean cpuCompatible = userCpuScore >= minCpuScore;
        boolean gpuCompatible = userGpuScore >= minGpuScore;


        boolean ramCompatible = extractNumber(userConfig.getRam()) >=
                (gameRequirements == null || gameRequirements.getRam() == null || gameRequirements.getRam().equals("N/A")
                        ? 1
                        : extractNumber(gameRequirements.getRam()));

        boolean storageCompatible = extractNumber(userConfig.getStorage()) >=
                (gameRequirements == null || gameRequirements.getStorage() == null || gameRequirements.getStorage().equals("N/A")
                        ? 1
                        : extractNumber(gameRequirements.getStorage()));


        boolean compatible = cpuCompatible && gpuCompatible && ramCompatible;


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

        double gameDifficultyFactor = Math.max(20, gpuScore / 80.0);


        double weightedPerformance = (gpuScore * 0.75 + cpuScore * 0.25) / gameDifficultyFactor;


        double qualityMultiplier = switch (quality.toLowerCase()) {
            case "low" -> 2.0;
            case "medium" -> 1.3;
            case "high" -> 0.8;
            default -> 1.0;
        };


        int estimatedFps = (int) Math.round(weightedPerformance * qualityMultiplier);


        estimatedFps = Math.max(15, Math.min(estimatedFps, 300));

        return estimatedFps + " FPS";
    }

    @Override
    public List<GameComparisonResponseDTO> getAllGameComparisons() {
        return gameComparisonRepository.findAll()
                .stream()
                .map(gameComparisonMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<GameComparisonResponseDTO> getGameComparisonsByAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Long userId = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();

        return gameComparisonRepository.findByConfiguration_UserId(userId)
                .stream()
                .map(gameComparisonMapper::toDTO)
                .collect(Collectors.toList());
    }

}

