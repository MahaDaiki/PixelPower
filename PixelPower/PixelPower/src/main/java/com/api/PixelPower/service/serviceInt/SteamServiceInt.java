package com.api.PixelPower.service.serviceInt;

import com.api.PixelPower.dto.GameDTO;

import java.util.List;

public interface SteamServiceInt {
    List<GameDTO> getGamesWithNames(int page, int size);
}
