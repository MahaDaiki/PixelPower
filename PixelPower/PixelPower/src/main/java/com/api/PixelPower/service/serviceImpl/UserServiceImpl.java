package com.api.PixelPower.service.serviceImpl;

import com.api.PixelPower.dto.response.AuthTokenResponseDTO;
import com.api.PixelPower.dto.request.LoginRequestDTO;
import com.api.PixelPower.dto.UserDTO;
import com.api.PixelPower.dto.response.UserResponseDTO;
import com.api.PixelPower.entity.Role;
import com.api.PixelPower.entity.User;
import com.api.PixelPower.exception.EmailAlreadyExistsException;
import com.api.PixelPower.exception.ResourceNotFoundException;
import com.api.PixelPower.mapper.UserMapper;
import com.api.PixelPower.repository.UserRepository;
import com.api.PixelPower.security.CustomUserDetails;
import com.api.PixelPower.service.serviceInt.UserServiceInt;
import com.api.PixelPower.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceInt {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public UserResponseDTO register(@Valid UserDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already in use");
        }
        if (userDTO.getRole() == null) {
            userDTO.setRole(Role.ROLE_USER);
        }

        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setRole(userDTO.getRole());
        newUser.setProfilePicture(userDTO.getProfilePicture());


        newUser = userRepository.save(newUser);


        return userMapper.toResponseDTO(newUser);
    }

    @Override
    public AuthTokenResponseDTO login(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(loginRequest.getEmail());

        return new AuthTokenResponseDTO(token);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toResponseDTO).toList();
    }

    @Override
    public Optional<UserResponseDTO> getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toResponseDTO);
    }

    @Override
    public UserResponseDTO getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateAuthenticatedUser(UserDTO userDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (userDTO.getUsername() != null) user.setUsername(userDTO.getUsername());
        if (userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());
        if (userDTO.getProfilePicture() != null) user.setProfilePicture(userDTO.getProfilePicture());
        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }


        user = userRepository.save(user);

        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateProfilePicture(MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        try {

            Path uploadDir = Paths.get("uploads/profile_pictures");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path targetLocation = uploadDir.resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            user.setProfilePicture(fileName);
            user = userRepository.save(user);

            return userMapper.toResponseDTO(user);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }



    @Override
    public UserResponseDTO updateAuthenticatedPassword(String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user = userRepository.save(user);

        return userMapper.toResponseDTO(user);
    }



    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);

    }




}
