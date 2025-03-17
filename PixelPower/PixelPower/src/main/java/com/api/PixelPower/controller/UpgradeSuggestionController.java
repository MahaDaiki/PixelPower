package com.api.PixelPower.controller;


import com.api.PixelPower.dto.response.UpgradeSuggestionResponseDTO;
import com.api.PixelPower.service.serviceInt.UpgradeSuggestionServiceInt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/upgrade-suggestions")
@RequiredArgsConstructor
public class UpgradeSuggestionController {
    private final UpgradeSuggestionServiceInt upgradeSuggestionService;

    @GetMapping
    public ResponseEntity<List<UpgradeSuggestionResponseDTO>> getAllUpgradeSuggestions() {
        List<UpgradeSuggestionResponseDTO> suggestions = upgradeSuggestionService.getAllUpgradeSuggestions();
        return ResponseEntity.ok(suggestions);
    }


    @GetMapping("/{comparisonId}")
    public ResponseEntity<List<UpgradeSuggestionResponseDTO>> getUpgradeSuggestionsByComparisonId(@PathVariable Long comparisonId) {
        List<UpgradeSuggestionResponseDTO> suggestions = upgradeSuggestionService.getUpgradeSuggestionsByComparisonId(comparisonId);
        return ResponseEntity.ok(suggestions);
    }


    @GetMapping("/generate/{comparisonId}")
    public ResponseEntity<List<UpgradeSuggestionResponseDTO>> generateUpgradeSuggestions(@PathVariable Long comparisonId) {
        List<UpgradeSuggestionResponseDTO> suggestions = upgradeSuggestionService.generateUpgradeSuggestions(comparisonId);
        return ResponseEntity.ok(suggestions);
    }

}
