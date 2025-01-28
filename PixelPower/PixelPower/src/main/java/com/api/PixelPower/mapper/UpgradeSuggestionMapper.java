package com.api.PixelPower.mapper;

import com.api.PixelPower.dto.UpgradeSuggestionDTO;
import com.api.PixelPower.entity.UpgradeSuggestion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpgradeSuggestionMapper {
    UpgradeSuggestionDTO toDTO(UpgradeSuggestion upgradeSuggestion);

//    @Mapping(source = "configurationId", target = "configuration.id")
    UpgradeSuggestion toEntity(UpgradeSuggestionDTO upgradeSuggestionDTO);
}
