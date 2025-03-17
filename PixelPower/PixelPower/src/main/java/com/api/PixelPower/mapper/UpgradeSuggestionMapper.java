package com.api.PixelPower.mapper;

import com.api.PixelPower.dto.response.UpgradeSuggestionResponseDTO;
import com.api.PixelPower.entity.UpgradeSuggestion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpgradeSuggestionMapper {
    UpgradeSuggestionResponseDTO toDTO(UpgradeSuggestion upgradeSuggestion);

//    @Mapping(source = "configurationId", target = "configuration.id")
    UpgradeSuggestion toEntity(UpgradeSuggestionResponseDTO upgradeSuggestionResponseDTO);
}
