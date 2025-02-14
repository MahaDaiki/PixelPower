package com.api.PixelPower.service.serviceInt;

import com.api.PixelPower.dto.ConfigurationDTO;
import com.api.PixelPower.dto.response.ConfigurationResponseDTO;

import java.util.List;

public interface ConfigurationServiceInt {
    ConfigurationResponseDTO createConfiguration(ConfigurationDTO dto);
    ConfigurationResponseDTO getConfigurationById(Long id);
    ConfigurationResponseDTO updateConfiguration(Long id, ConfigurationDTO dto);
    void deleteConfiguration(Long id);
    List<ConfigurationResponseDTO> getConfigurationsByAuthenticatedUser();
}
