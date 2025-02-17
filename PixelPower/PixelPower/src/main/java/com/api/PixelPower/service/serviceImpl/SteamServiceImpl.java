package com.api.PixelPower.service.serviceImpl;

import com.api.PixelPower.dto.GameDTO;
import com.api.PixelPower.dto.response.SteamApiResponse;
import com.api.PixelPower.mapper.GameMapper;
import com.api.PixelPower.service.serviceInt.SteamServiceInt;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SteamServiceImpl implements SteamServiceInt {
    private static final String STEAM_GAME_LIST_URL = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";

    private final RestTemplate restTemplate;
    private final GameMapper gameMapper;

    @Override

    @Cacheable(value = "allGames")
    public List<GameDTO> getGamesWithNames(int page, int size) {
        System.out.println("getGamesWithNames");
        try {
            SteamApiResponse response = restTemplate.getForObject(STEAM_GAME_LIST_URL, SteamApiResponse.class);

            if (response != null && response.getApplist() != null) {
                List<GameDTO> allGames = response.getApplist().getApps().stream()
                        .filter(game -> game.getName() != null && !game.getName().isEmpty()) // Only keep named games
                        .map(gameMapper::toDTO)
                        .collect(Collectors.toList());


                int fromIndex = Math.max(0, page * size);
                int toIndex = Math.min(fromIndex + size, allGames.size());

                if (fromIndex >= allGames.size()) {
                    return List.of();
                }

                return allGames.subList(fromIndex, toIndex);
            }
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while fetching data from Steam API.", e);
        }
        return List.of();
    }

    @Override
    @Cacheable(value = "gameDetails", key = "#appId")
    public Map<String, Object> getGameDetails(int appId) {
        String url = "https://store.steampowered.com/api/appdetails?appids=" + appId;
        System.out.println("Requesting URL: " + url);
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.out.println("Error response from Steam API: " + e.getMessage());
            throw new RuntimeException("Failed to fetch game details: " + e.getStatusCode());
        }
    }
}
