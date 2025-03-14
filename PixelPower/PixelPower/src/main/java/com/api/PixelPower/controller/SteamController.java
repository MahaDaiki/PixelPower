package com.api.PixelPower.controller;

import com.api.PixelPower.dto.GameDTO;
import com.api.PixelPower.dto.response.GameRequirementsResponseDTO;
import com.api.PixelPower.service.serviceInt.GameRequirementServiceInt;
import com.api.PixelPower.service.serviceInt.SteamServiceInt;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/games")
@AllArgsConstructor
public class SteamController {
    private final SteamServiceInt steamService;
    private final GameRequirementServiceInt gameRequirementService;

    @GetMapping("")
    public ResponseEntity<List<GameDTO>> getGamesWithNames(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

            List<GameDTO> games = steamService.getGamesWithNames(page - 1, size);
            return ResponseEntity.ok(games);


    }

    @GetMapping("/{appId}")
    public ResponseEntity<Map<String, Object>> getGameDetails(@PathVariable int appId) {
        System.out.println(appId);
        Map<String, Object> gameDetails = steamService.getGameDetails(appId);
        return ResponseEntity.ok(gameDetails);
    }

    @GetMapping("/requirements/{appId}/{userId}")
    public ResponseEntity<GameRequirementsResponseDTO> getGameRequirements(
            @PathVariable int appId,
            @PathVariable Long userId) {

        GameRequirementsResponseDTO responseDTO = gameRequirementService.parseRequirements(appId, userId);
        return ResponseEntity.ok(responseDTO);
    }


    @GetMapping("/search")
    public ResponseEntity<List<GameDTO>> searchGames(@RequestParam String name) {
        List<GameDTO> results = steamService.searchGamesByName(name);
        return ResponseEntity.ok(results);
    }
}
