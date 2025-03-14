package com.api.PixelPower.service.serviceInt;

import com.api.PixelPower.dto.GameDTO;

import java.util.List;
import java.util.Map;

public interface SteamServiceInt {
    List<GameDTO> getGamesWithNames(int page, int size);
    Map<String, Object> getGameDetails(int appId);
    Integer getAppIdByGameName(String gameName);
    List<GameDTO> searchGamesByName(String query);
}
