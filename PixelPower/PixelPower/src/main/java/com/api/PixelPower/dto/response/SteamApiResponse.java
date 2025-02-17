package com.api.PixelPower.dto.response;

import com.api.PixelPower.dto.GameDTO;
import lombok.Data;

import java.util.List;

@Data
public class SteamApiResponse {
    private AppList applist;

    @Data
    public static class AppList {
        private List<GameDTO> apps;
    }
}
