package com.api.PixelPower.service.serviceImpl;

import com.api.PixelPower.dto.response.UpgradeSuggestionResponseDTO;
import com.api.PixelPower.dto.response.GameRequirementsResponseDTO;
import com.api.PixelPower.entity.GameComparison;
import com.api.PixelPower.entity.UpgradeSuggestion;
import com.api.PixelPower.mapper.UpgradeSuggestionMapper;
import com.api.PixelPower.repository.GameComparisonRepository;
import com.api.PixelPower.repository.UpgradeSuggestionRepository;
import com.api.PixelPower.service.serviceInt.GameRequirementServiceInt;
import com.api.PixelPower.service.serviceInt.SteamServiceInt;
import com.api.PixelPower.service.serviceInt.UpgradeSuggestionServiceInt;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UpgradeSuggestionServiceImpl implements UpgradeSuggestionServiceInt {
    private final UpgradeSuggestionRepository upgradeSuggestionRepository;
    private final GameComparisonRepository gameComparisonRepository;
    private final GameRequirementServiceInt gameRequirementService;
    private final UpgradeSuggestionMapper mapper;
    private final SteamServiceInt steamService;

    @Override
    public List<UpgradeSuggestionResponseDTO> getAllUpgradeSuggestions() {
        return upgradeSuggestionRepository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UpgradeSuggestionResponseDTO> getUpgradeSuggestionsByComparisonId(Long comparisonId) {
        return upgradeSuggestionRepository.findByGameComparisonId(comparisonId).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UpgradeSuggestionResponseDTO> generateUpgradeSuggestions(Long comparisonId) {
        GameComparison comparison = gameComparisonRepository.findById(comparisonId)
                .orElseThrow(() -> new RuntimeException("GameComparison not found"));



        if (comparison.getCompatible()) {
            return new ArrayList<>();
        }

        Integer appId = steamService.getAppIdByGameName(comparison.getGameName());

        System.out.println("Steam App ID for " + comparison.getGameName() + ": " + appId);


        GameRequirementsResponseDTO gameRequirements = gameRequirementService.parseRequirements(
                appId, comparison.getConfiguration().getUser().getId()
        );
        System.out.println("test empty ?" + comparison.getConfiguration().getUser().getId());
        System.out.println(gameRequirements + " why empty ");
        List<UpgradeSuggestion> upgradeSuggestions = new ArrayList<>();

        if (!comparison.getCpuCompatible() && gameRequirements.getMinCpu() != null && !gameRequirements.getMinCpu().isEmpty()) {
            System.out.println("Adding CPU upgrade suggestion: " + gameRequirements.getMinCpu());
            upgradeSuggestions.add(
                    UpgradeSuggestion.builder()
                            .limitingComponent("CPU")
                            .suggestedUpgrade(gameRequirements.getMinCpu())
                            .gameComparison(comparison)
                            .build()
            );
        }

        if (!comparison.getGpuCompatible() && gameRequirements.getMinGpu() != null && !gameRequirements.getMinGpu().isEmpty()) {
            System.out.println("Adding GPU upgrade suggestion: " + gameRequirements.getMinGpu());
            upgradeSuggestions.add(
                    UpgradeSuggestion.builder()
                            .limitingComponent("GPU")
                            .suggestedUpgrade(gameRequirements.getMinGpu())
                            .gameComparison(comparison)
                            .build()
            );
        }

        if (!comparison.getRamCompatible() && gameRequirements.getRam() != null && !gameRequirements.getRam().isEmpty()) {
            upgradeSuggestions.add(
                    UpgradeSuggestion.builder()
                            .limitingComponent("RAM")
                            .suggestedUpgrade(gameRequirements.getRam())
                            .gameComparison(comparison)
                            .build()
            );
        }


        if (!upgradeSuggestions.isEmpty()) {
            System.out.println("Saving upgrade suggestions: " + upgradeSuggestions.size());
            upgradeSuggestionRepository.saveAll(upgradeSuggestions);
            System.out.println("CPU Compatibility: " + comparison.getCpuCompatible());
            System.out.println("GPU Compatibility: " + comparison.getGpuCompatible());
            System.out.println("RAM Compatibility: " + comparison.getRamCompatible());
        } else {
            System.out.println("No upgrade suggestions to save.");
            System.out.println("CPU Compatibility: " + comparison.getCpuCompatible());
            System.out.println("GPU Compatibility: " + gameRequirements.getMinGpu());
            System.out.println("RAM Compatibility: " + comparison.getRamCompatible());

        }
        return upgradeSuggestions.stream()
                .map(suggestion -> new UpgradeSuggestionResponseDTO(
                        suggestion.getId(),
                        suggestion.getLimitingComponent(),
                        suggestion.getSuggestedUpgrade(),
                        suggestion.getGameComparison().getId()
                ))
                .toList();
    }
}
