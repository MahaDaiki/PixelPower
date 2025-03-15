package com.api.PixelPower.controller;

import com.api.PixelPower.dto.response.GameComparisonResponseDTO;
import com.api.PixelPower.service.serviceInt.GameComparisonServiceInt;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/game-comparison")
@AllArgsConstructor
public class GameComparisonController {
    private final GameComparisonServiceInt gameComparisonService;

    @GetMapping("/compare/{appId}")
    public ResponseEntity<GameComparisonResponseDTO> compareGameWithUserConfig(
            @PathVariable("appId") int appId) {

        GameComparisonResponseDTO comparisonResult = gameComparisonService.compareGameWithUserConfig(appId);
        return ResponseEntity.ok(comparisonResult);
    }

    @GetMapping
    public List<GameComparisonResponseDTO> getAllGameComparisons() {
        return gameComparisonService.getAllGameComparisons();
    }

    @GetMapping("/user")
    public List<GameComparisonResponseDTO> getGameComparisonsByAuthenticatedUser() {
        return gameComparisonService.getGameComparisonsByAuthenticatedUser();
    }
}
