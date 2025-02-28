package com.api.PixelPower.controller;

import com.api.PixelPower.dto.GameComparisonDTO;
import com.api.PixelPower.service.serviceInt.GameComparisonServiceInt;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game-comparison")
@AllArgsConstructor
public class GameComparisonController {
    private final GameComparisonServiceInt gameComparisonService;

    @GetMapping("/compare/{appId}/{userId}")
    public ResponseEntity<GameComparisonDTO> compareGameWithUserConfig(
            @PathVariable("appId") int appId,
            @PathVariable("userId") Long userId) {

        GameComparisonDTO comparisonResult = gameComparisonService.compareGameWithUserConfig(appId, userId);
        return ResponseEntity.ok(comparisonResult);
    }
}
