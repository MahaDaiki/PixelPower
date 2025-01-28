package com.api.PixelPower.mapper;


import com.api.PixelPower.dto.GameComparisonDTO;
import com.api.PixelPower.entity.GameComparison;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameComparisonMapper {
    GameComparisonDTO toDTO(GameComparison gameComparison);

//    @Mapping(source = "configurationId", target = "configuration.id")
    GameComparison toEntity(GameComparisonDTO gameComparisonDTO);
}
