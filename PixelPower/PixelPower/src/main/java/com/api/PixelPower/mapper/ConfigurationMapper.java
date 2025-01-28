package com.api.PixelPower.mapper;


import com.api.PixelPower.dto.ConfigurationDTO;
import com.api.PixelPower.entity.Configuration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConfigurationMapper {
    ConfigurationDTO toDTO(Configuration configuration);
    Configuration toEntity(ConfigurationDTO configurationDTO);
}
