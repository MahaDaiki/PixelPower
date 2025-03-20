package com.api.PixelPower.service.serviceImpl;

import com.api.PixelPower.dto.UserDTO;
import com.api.PixelPower.dto.request.LoginRequestDTO;
import com.api.PixelPower.dto.response.AuthTokenResponseDTO;
import com.api.PixelPower.dto.response.UserResponseDTO;
import com.api.PixelPower.entity.Role;
import com.api.PixelPower.entity.User;
import com.api.PixelPower.exception.EmailAlreadyExistsException;
import com.api.PixelPower.exception.ResourceNotFoundException;
import com.api.PixelPower.mapper.UserMapper;
import com.api.PixelPower.repository.UserRepository;
import com.api.PixelPower.security.CustomUserDetails;
import com.api.PixelPower.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UserDTO testUserDTO;
    private UserResponseDTO testUserResponseDTO;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("encodedPassword");
        testUser.setRole(Role.ROLE_USER);

        testUserDTO = new UserDTO();
        testUserDTO.setUsername("testUser");
        testUserDTO.setEmail("test@example.com");
        testUserDTO.setPassword("password123");
        testUserDTO.setRole(Role.ROLE_USER);

        testUserResponseDTO = new UserResponseDTO();
        testUserResponseDTO.setId(1L);
        testUserResponseDTO.setUsername("testUser");
        testUserResponseDTO.setEmail("test@example.com");
        testUserResponseDTO.setRole(Role.ROLE_USER);
    }

    @Test
    void register_ShouldCreateNewUser() {
        // Given
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toResponseDTO(any(User.class))).thenReturn(testUserResponseDTO);

        // When
        UserResponseDTO result = userService.register(testUserDTO);

        // Then
        assertNotNull(result);
        assertEquals(testUserResponseDTO.getId(), result.getId());
        assertEquals(testUserResponseDTO.getUsername(), result.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_WhenEmailExists_ShouldThrowException() {
        // Given
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // When & Then
        assertThrows(EmailAlreadyExistsException.class, () -> userService.register(testUserDTO));
        verify(userRepository, never()).save(any(User.class));
    }

//    @Test
//    void login_ShouldReturnAuthToken() {
//        LoginRequestDTO loginRequest = new LoginRequestDTO();
//        loginRequest.setEmail("test@example.com");
//        loginRequest.setPassword("password123");
//
//        CustomUserDetails userDetails = mock(CustomUserDetails.class);
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//                .thenReturn(authentication);
//        when(authentication.getPrincipal()).thenReturn(userDetails);
//        when(jwtUtil.generateToken(anyString())).thenReturn("test-jwt-token");
//
//        AuthTokenResponseDTO result = userService.login(loginRequest);
//
//        assertNotNull(result);
//        assertEquals("test-jwt-token", result.getToken());
//        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
//        verify(jwtUtil).generateToken(loginRequest.getEmail());
//    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toResponseDTO(any(User.class))).thenReturn(testUserResponseDTO);

        List<UserResponseDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testUserResponseDTO.getId(), result.get(0).getId());
        verify(userRepository).findAll();
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(userMapper.toResponseDTO(any(User.class))).thenReturn(testUserResponseDTO);

        Optional<UserResponseDTO> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(testUserResponseDTO.getId(), result.get().getId());
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldReturnEmpty() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<UserResponseDTO> result = userService.getUserById(1L);

        assertFalse(result.isPresent());
        verify(userRepository).findById(1L);
    }

    @Test
    void getAuthenticatedUser_ShouldReturnCurrentUser() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));
        when(userMapper.toResponseDTO(any(User.class))).thenReturn(testUserResponseDTO);

        UserResponseDTO result = userService.getAuthenticatedUser();

        assertNotNull(result);
        assertEquals(testUserResponseDTO.getId(), result.getId());
        verify(userRepository).findByUsername("testUser");
    }

    @Test
    void getAuthenticatedUser_WhenUserNotFound_ShouldThrowException() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getAuthenticatedUser());
        verify(userRepository).findByUsername("testUser");
    }

    @Test
    void updateAuthenticatedUser_ShouldUpdateUser() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.encode(anyString())).thenReturn("newEncodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toResponseDTO(any(User.class))).thenReturn(testUserResponseDTO);

        UserDTO updateDTO = new UserDTO();
        updateDTO.setUsername("updatedUsername");
        updateDTO.setEmail("updated@example.com");
        updateDTO.setPassword("newPassword");

        UserResponseDTO result = userService.updateAuthenticatedUser(updateDTO);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode("newPassword");
    }



    @Test
    void updateAuthenticatedPassword_ShouldUpdatePassword() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.encode(anyString())).thenReturn("newEncodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toResponseDTO(any(User.class))).thenReturn(testUserResponseDTO);

        UserResponseDTO result = userService.updateAuthenticatedPassword("newPassword");

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode("newPassword");
    }

    @Test
    void deleteUser_ShouldDeleteUser() {
        doNothing().when(userRepository).deleteById(anyLong());

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }
}