package com.api.PixelPower.service.serviceImpl;

import com.api.PixelPower.dto.ConfigurationDTO;
import com.api.PixelPower.dto.ConfigurationResponseDTO;
import com.api.PixelPower.entity.Configuration;
import com.api.PixelPower.entity.User;
import com.api.PixelPower.exception.EmptyException;
import com.api.PixelPower.exception.ResourceNotFoundException;
import com.api.PixelPower.mapper.ConfigurationMapper;
import com.api.PixelPower.repository.ConfigurationRepository;
import com.api.PixelPower.repository.UserRepository;
import com.api.PixelPower.service.serviceInt.ConfigurationServiceInt;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ConfigurationServiceImpl implements ConfigurationServiceInt {
    private final ConfigurationRepository configurationRepository;
    private final UserRepository userRepository;
    private final ConfigurationMapper configurationMapper;
    @Override
    public ConfigurationResponseDTO createConfiguration(ConfigurationDTO dto) {
        if (dto.getName() == null || dto.getCpu() == null || dto.getGpu() == null) {
            throw new EmptyException("Required fields cannot be empty.");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Configuration configuration = configurationMapper.toEntity(dto);
        configuration.setUser(user);

        Configuration savedConfiguration = configurationRepository.save(configuration);

        user.getConfigurations().add(savedConfiguration);
        userRepository.save(user);

        return configurationMapper.mapToResponseDTO(savedConfiguration);
    }

    @Override
    public ConfigurationResponseDTO getConfigurationById(Long id) {
        Configuration config = configurationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuration not found"));

        return configurationMapper.mapToResponseDTO(config);
    }

    @Override
    public ConfigurationResponseDTO updateConfiguration(Long id, ConfigurationDTO dto) {
        Configuration config = configurationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuration not found"));

        config.setName(dto.getName());
        config.setCpu(dto.getCpu());
        config.setGpu(dto.getGpu());
        config.setRam(dto.getRam());
        config.setStorage(dto.getStorage());
        config.setOs(dto.getOs());
        config.setStatus(dto.getStatus());

        Configuration updatedConfig = configurationRepository.save(config);
        return configurationMapper.mapToResponseDTO(updatedConfig);
    }

    @Override
    public void deleteConfiguration(Long id) {

        Configuration config = configurationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuration not found"));

        configurationRepository.delete(config);
    }



    @Override
    public List<ConfigurationResponseDTO> getConfigurationsByAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return user.getConfigurations().stream()
                .map(configurationMapper::mapToResponseDTO)
                .toList();
    }
}
