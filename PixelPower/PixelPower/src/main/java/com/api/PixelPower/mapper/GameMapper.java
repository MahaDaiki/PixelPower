package com.api.PixelPower.mapper;

import com.api.PixelPower.dto.GameDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface GameMapper {
    GameDTO toDTO(GameDTO game);
}
