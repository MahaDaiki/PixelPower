package com.api.PixelPower.service.serviceInt;

import com.api.PixelPower.dto.ConfigurationDTO;
import com.api.PixelPower.dto.response.ConfigurationResponseDTO;
import com.api.PixelPower.entity.Configuration;
import com.api.PixelPower.entity.OperatingSystem;

import java.util.List;

public interface ConfigurationServiceInt {
    ConfigurationResponseDTO createConfiguration(ConfigurationDTO dto);
    ConfigurationResponseDTO getConfigurationById(Long id);
    ConfigurationResponseDTO updateConfiguration(Long id, ConfigurationDTO dto);
    void deleteConfiguration(Long id);
    OperatingSystem getPrimaryUserOs(Long userId);
    Configuration getUserPrimaryConfiguration(Long userId);
    List<ConfigurationResponseDTO> getConfigurationsByAuthenticatedUser();
}
