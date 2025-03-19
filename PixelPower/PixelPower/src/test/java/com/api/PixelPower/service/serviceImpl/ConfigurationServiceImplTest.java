package com.api.PixelPower.service.serviceImpl;

import com.api.PixelPower.dto.request.ConfigurationRequestDTO;
import com.api.PixelPower.dto.response.ConfigurationResponseDTO;
import com.api.PixelPower.entity.*;
import com.api.PixelPower.exception.EmptyException;
import com.api.PixelPower.exception.ResourceNotFoundException;
import com.api.PixelPower.mapper.ConfigurationMapper;
import com.api.PixelPower.repository.ConfigurationRepository;
import com.api.PixelPower.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ConfigurationServiceImplTest {

    @Mock
    private ConfigurationRepository configurationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConfigurationMapper configurationMapper;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ConfigurationServiceImpl configurationService;

    private User testUser;
    private Configuration testConfiguration;
    private ConfigurationRequestDTO testRequestDTO;
    private ConfigurationResponseDTO testResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setConfigurations(new ArrayList<>());

        testConfiguration = new Configuration();
        testConfiguration.setId(1L);
        testConfiguration.setName("Gaming PC");
        testConfiguration.setCpu("Intel i7");
        testConfiguration.setGpu("NVIDIA RTX 3080");
        testConfiguration.setRam("16GB");
        testConfiguration.setStorage("1TB SSD");
        testConfiguration.setOs(OperatingSystem.WINDOWS);
        testConfiguration.setStatus(ConfigStatus.PRIMARY);
        testConfiguration.setUser(testUser);
        testConfiguration.setGameComparisons(new ArrayList<>());

        testRequestDTO = new ConfigurationRequestDTO();
        testRequestDTO.setName("Gaming PC");
        testRequestDTO.setCpu("Intel i7");
        testRequestDTO.setGpu("NVIDIA RTX 3080");
        testRequestDTO.setRam("16GB");
        testRequestDTO.setStorage("1TB SSD");
        testRequestDTO.setOs(OperatingSystem.WINDOWS);
        testRequestDTO.setStatus(ConfigStatus.PRIMARY);

        testResponseDTO = new ConfigurationResponseDTO();
        testResponseDTO.setId(1L);
        testResponseDTO.setName("Gaming PC");
        testResponseDTO.setCpu("Intel i7");
        testResponseDTO.setGpu("NVIDIA RTX 3080");
        testResponseDTO.setRam("16GB");
        testResponseDTO.setStorage("1TB SSD");
        testResponseDTO.setOs(OperatingSystem.WINDOWS);
        testResponseDTO.setStatus(ConfigStatus.PRIMARY);


        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void createConfiguration_ShouldCreateAndReturnConfiguration() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(configurationMapper.toEntity(any(ConfigurationRequestDTO.class))).thenReturn(testConfiguration);
        when(configurationRepository.save(any(Configuration.class))).thenReturn(testConfiguration);
        when(configurationMapper.mapToResponseDTO(any(Configuration.class))).thenReturn(testResponseDTO);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        ConfigurationResponseDTO result = configurationService.createConfiguration(testRequestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Gaming PC", result.getName());
        assertEquals("Intel i7", result.getCpu());
        assertEquals("NVIDIA RTX 3080", result.getGpu());
        verify(configurationRepository, times(1)).save(any(Configuration.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createConfiguration_WithEmptyFields_ShouldThrowException() {
        ConfigurationRequestDTO emptyRequestDTO = new ConfigurationRequestDTO();
        emptyRequestDTO.setName(null);
        emptyRequestDTO.setCpu(null);
        emptyRequestDTO.setGpu("NVIDIA RTX 3080");

        assertThrows(EmptyException.class, () -> {
            configurationService.createConfiguration(emptyRequestDTO);
        });
        verify(configurationRepository, never()).save(any(Configuration.class));
    }

    @Test
    void createConfiguration_WithNonExistentUser_ShouldThrowException() {

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            configurationService.createConfiguration(testRequestDTO);
        });
        verify(configurationRepository, never()).save(any(Configuration.class));
    }

    @Test
    void getConfigurationById_ShouldReturnConfiguration() {

        when(configurationRepository.findById(anyLong())).thenReturn(Optional.of(testConfiguration));
        when(configurationMapper.mapToResponseDTO(any(Configuration.class))).thenReturn(testResponseDTO);

        ConfigurationResponseDTO result = configurationService.getConfigurationById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Gaming PC", result.getName());
        assertEquals("Intel i7", result.getCpu());
        assertEquals("NVIDIA RTX 3080", result.getGpu());
        verify(configurationRepository, times(1)).findById(1L);
    }

    @Test
    void getConfigurationById_WithNonExistentId_ShouldThrowException() {

        when(configurationRepository.findById(anyLong())).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> {
            configurationService.getConfigurationById(1L);
        });
    }

    @Test
    void updateConfiguration_ShouldUpdateAndReturnConfiguration() {

        when(configurationRepository.findById(anyLong())).thenReturn(Optional.of(testConfiguration));
        when(configurationRepository.save(any(Configuration.class))).thenReturn(testConfiguration);
        when(configurationMapper.mapToResponseDTO(any(Configuration.class))).thenReturn(testResponseDTO);

        ConfigurationRequestDTO updateRequestDTO = new ConfigurationRequestDTO();
        updateRequestDTO.setName("Updated PC");
        updateRequestDTO.setCpu("AMD Ryzen 9");
        updateRequestDTO.setGpu("NVIDIA RTX 4080");
        updateRequestDTO.setRam("32GB");
        updateRequestDTO.setStorage("2TB SSD");
        updateRequestDTO.setOs(OperatingSystem.LINUX);
        updateRequestDTO.setStatus(ConfigStatus.SECONDARY);


        ConfigurationResponseDTO result = configurationService.updateConfiguration(1L, updateRequestDTO);


        assertNotNull(result);
        verify(configurationRepository, times(1)).findById(1L);
        verify(configurationRepository, times(1)).save(any(Configuration.class));


        assertEquals("Updated PC", testConfiguration.getName());
        assertEquals("AMD Ryzen 9", testConfiguration.getCpu());
        assertEquals("NVIDIA RTX 4080", testConfiguration.getGpu());
        assertEquals("32GB", testConfiguration.getRam());
        assertEquals("2TB SSD", testConfiguration.getStorage());
        assertEquals(OperatingSystem.LINUX, testConfiguration.getOs());
        assertEquals(ConfigStatus.SECONDARY, testConfiguration.getStatus());
    }

    @Test
    void updateConfiguration_WithNonExistentId_ShouldThrowException() {

        when(configurationRepository.findById(anyLong())).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> {
            configurationService.updateConfiguration(1L, testRequestDTO);
        });
        verify(configurationRepository, never()).save(any(Configuration.class));
    }

    @Test
    void deleteConfiguration_ShouldDeleteConfiguration() {

        when(configurationRepository.findById(anyLong())).thenReturn(Optional.of(testConfiguration));
        doNothing().when(configurationRepository).delete(any(Configuration.class));


        configurationService.deleteConfiguration(1L);


        verify(configurationRepository, times(1)).findById(1L);
        verify(configurationRepository, times(1)).delete(testConfiguration);
    }

    @Test
    void deleteConfiguration_WithNonExistentId_ShouldThrowException() {

        when(configurationRepository.findById(anyLong())).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> {
            configurationService.deleteConfiguration(1L);
        });
        verify(configurationRepository, never()).delete(any(Configuration.class));
    }

    @Test
    void getConfigurationsByAuthenticatedUser_ShouldReturnUserConfigurations() {

        testUser.getConfigurations().add(testConfiguration);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(configurationMapper.mapToResponseDTO(any(Configuration.class))).thenReturn(testResponseDTO);


        List<ConfigurationResponseDTO> result = configurationService.getConfigurationsByAuthenticatedUser();


        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Gaming PC", result.get(0).getName());
        assertEquals("Intel i7", result.get(0).getCpu());
        assertEquals("NVIDIA RTX 3080", result.get(0).getGpu());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void getConfigurationsByAuthenticatedUser_WithNonExistentUser_ShouldThrowException() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            configurationService.getConfigurationsByAuthenticatedUser();
        });
    }

    @Test
    void getPrimaryUserOs_ShouldReturnUserPrimaryOs() {

        when(configurationRepository.findByUserId(anyLong())).thenReturn(Collections.singletonList(testConfiguration));

        OperatingSystem result = configurationService.getPrimaryUserOs(1L);

        assertEquals(OperatingSystem.WINDOWS, result);
        verify(configurationRepository, times(1)).findByUserId(1L);
    }

    @Test
    void getPrimaryUserOs_WithNoConfigurations_ShouldThrowException() {

        when(configurationRepository.findByUserId(anyLong())).thenReturn(Collections.emptyList());


        assertThrows(RuntimeException.class, () -> {
            configurationService.getPrimaryUserOs(1L);
        });
    }

    @Test
    void getUserPrimaryConfiguration_ShouldReturnPrimaryConfiguration() {

        when(configurationRepository.findByUserId(anyLong())).thenReturn(Collections.singletonList(testConfiguration));


        Configuration result = configurationService.getUserPrimaryConfiguration(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Gaming PC", result.getName());
        assertEquals(ConfigStatus.PRIMARY, result.getStatus());
        verify(configurationRepository, times(1)).findByUserId(1L);
    }

    @Test
    void getUserPrimaryConfiguration_WithNoConfigurations_ShouldThrowException() {

        when(configurationRepository.findByUserId(anyLong())).thenReturn(Collections.emptyList());


        assertThrows(RuntimeException.class, () -> {
            configurationService.getUserPrimaryConfiguration(1L);
        });
    }

    @Test
    void getUserPrimaryConfiguration_WithOnlySecondaryConfigurations_ShouldThrowException() {

        Configuration secondaryConfig = new Configuration();
        secondaryConfig.setId(2L);
        secondaryConfig.setStatus(ConfigStatus.SECONDARY);

        when(configurationRepository.findByUserId(anyLong())).thenReturn(Collections.singletonList(secondaryConfig));


        assertThrows(RuntimeException.class, () -> {
            configurationService.getUserPrimaryConfiguration(1L);
        });
    }
}