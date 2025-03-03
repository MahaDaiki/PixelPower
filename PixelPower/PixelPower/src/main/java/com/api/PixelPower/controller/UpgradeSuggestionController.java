package com.api.PixelPower.controller;


import com.api.PixelPower.dto.UpgradeSuggestionDTO;
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
    public ResponseEntity<List<UpgradeSuggestionDTO>> getAllUpgradeSuggestions() {
        List<UpgradeSuggestionDTO> suggestions = upgradeSuggestionService.getAllUpgradeSuggestions();
        return ResponseEntity.ok(suggestions);
    }


    @GetMapping("/{comparisonId}")
    public ResponseEntity<List<UpgradeSuggestionDTO>> getUpgradeSuggestionsByComparisonId(@PathVariable Long comparisonId) {
        List<UpgradeSuggestionDTO> suggestions = upgradeSuggestionService.getUpgradeSuggestionsByComparisonId(comparisonId);
        return ResponseEntity.ok(suggestions);
    }


    @PostMapping("/generate/{comparisonId}")
    public ResponseEntity<String> generateUpgradeSuggestions(@PathVariable Long comparisonId) {
        upgradeSuggestionService.generateUpgradeSuggestions(comparisonId);
        return ResponseEntity.ok("Upgrade suggestions generated successfully.");
    }
}
