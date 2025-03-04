package com.api.PixelPower.mapper;


import com.api.PixelPower.dto.request.ConfigurationRequestDTO;
import com.api.PixelPower.dto.response.ConfigurationResponseDTO;
import com.api.PixelPower.entity.Configuration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConfigurationMapper {
    ConfigurationRequestDTO toDTO(Configuration configuration);
    Configuration toEntity(ConfigurationRequestDTO configurationRequestDTO);

    @Mapping(source = "user.id", target = "userId")
    ConfigurationResponseDTO mapToResponseDTO(Configuration config);
}
