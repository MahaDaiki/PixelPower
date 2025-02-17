package com.api.PixelPower.controller;

import com.api.PixelPower.dto.GameDTO;
import com.api.PixelPower.service.serviceInt.SteamServiceInt;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@AllArgsConstructor
public class SteamController {
    private final SteamServiceInt steamService;

    @GetMapping("")
    public ResponseEntity<List<GameDTO>> getGamesWithNames(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

            List<GameDTO> games = steamService.getGamesWithNames(page - 1, size);
            return ResponseEntity.ok(games);


    }

}
