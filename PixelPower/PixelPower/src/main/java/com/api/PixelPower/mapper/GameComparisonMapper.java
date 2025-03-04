package com.api.PixelPower.mapper;


import com.api.PixelPower.dto.response.GameComparisonResponseDTO;
import com.api.PixelPower.entity.GameComparison;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GameComparisonMapper {
    @Mapping(source = "configuration.id", target = "configurationId")
    GameComparisonResponseDTO toDTO(GameComparison gameComparison);

    @Mapping(source = "configurationId", target = "configuration.id")
    GameComparison toEntity(GameComparisonResponseDTO gameComparisonResponseDTO);
}
