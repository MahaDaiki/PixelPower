package com.api.PixelPower.service.serviceImpl;

import com.api.PixelPower.dto.GameDTO;
import com.api.PixelPower.dto.response.SteamApiResponse;
import com.api.PixelPower.exception.GameNotFoundException;
import com.api.PixelPower.exception.SteamApiException;
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
import java.util.Optional;
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
                        .filter(game -> game.getName() != null && !game.getName().isEmpty())
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
            throw new SteamApiException("Failed to fetch game list from Steam API." + e);
        }
        throw new SteamApiException("Steam API response was null.");
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
            throw new SteamApiException("Failed to fetch game details: " + e.getStatusCode());
        }
    }

    @Override
    public Integer getAppIdByGameName(String gameName) {
        List<GameDTO> allGames = getGamesWithNames(0, Integer.MAX_VALUE);

        // First, check for an exact match
        Optional<GameDTO> exactMatch = allGames.stream()
                .filter(game -> game.getName().equalsIgnoreCase(gameName))
                .findFirst();

        if (exactMatch.isPresent()) {
            return exactMatch.get().getAppid();
        }

        // If no exact match, try to find the closest match (containsIgnoreCase)
        List<GameDTO> closeMatches = allGames.stream()
                .filter(game -> game.getName().toLowerCase().contains(gameName.toLowerCase()))
                .collect(Collectors.toList());

        if (!closeMatches.isEmpty()) {
            return closeMatches.get(0).getAppid(); // Return the first closest match
        }

        throw new GameNotFoundException("Game not found: " + gameName);
    }

    @Override
    public List<GameDTO> searchGamesByName(String query, int page, int size) {
        List<GameDTO> allGames = getGamesWithNames(0, Integer.MAX_VALUE);

        List<GameDTO> matchingGames = allGames.stream()
                .filter(game -> game.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        int fromIndex = Math.max(0, page * size);
        int toIndex = Math.min(fromIndex + size, matchingGames.size());

        return fromIndex >= matchingGames.size() ? List.of() : matchingGames.subList(fromIndex, toIndex);
    }
}
