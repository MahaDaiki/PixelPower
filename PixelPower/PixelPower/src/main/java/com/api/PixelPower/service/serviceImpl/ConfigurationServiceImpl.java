package com.api.PixelPower.service.serviceImpl;

import com.api.PixelPower.dto.request.ConfigurationRequestDTO;
import com.api.PixelPower.dto.response.ConfigurationResponseDTO;
import com.api.PixelPower.entity.ConfigStatus;
import com.api.PixelPower.entity.Configuration;
import com.api.PixelPower.entity.OperatingSystem;
import com.api.PixelPower.entity.User;
import com.api.PixelPower.exception.EmptyException;
import com.api.PixelPower.exception.ResourceNotFoundException;
import com.api.PixelPower.mapper.ConfigurationMapper;
import com.api.PixelPower.repository.ConfigurationRepository;
import com.api.PixelPower.repository.UserRepository;
import com.api.PixelPower.service.serviceInt.ConfigurationServiceInt;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ConfigurationServiceImpl implements ConfigurationServiceInt {
    private final ConfigurationRepository configurationRepository;
    private final UserRepository userRepository;
    private final ConfigurationMapper configurationMapper;
    @Override
    public ConfigurationResponseDTO createConfiguration(ConfigurationRequestDTO dto) {
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
    public ConfigurationResponseDTO updateConfiguration(Long id, ConfigurationRequestDTO dto) {
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


    @Override
    public OperatingSystem getPrimaryUserOs(Long userId) {
        return getUserPrimaryConfiguration(userId).getOs();
    }

    @Override
    public Configuration getUserPrimaryConfiguration(Long userId) {
        List<Configuration> configurations = configurationRepository.findByUserId(userId);

        return configurations.stream()
                .filter(config -> config.getStatus() == ConfigStatus.PRIMARY)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No primary configuration found for user!"));
    }}

