package com.api.PixelPower.service.serviceImpl;

import com.api.PixelPower.dto.AuthTokenResponseDTO;
import com.api.PixelPower.dto.LoginRequestDTO;
import com.api.PixelPower.dto.UserDTO;
import com.api.PixelPower.dto.UserResponseDTO;
import com.api.PixelPower.entity.Role;
import com.api.PixelPower.entity.User;
import com.api.PixelPower.exception.EmailAlreadyExistsException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

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
    public UserResponseDTO updateUser(Long id, UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = existingUser.get();
        if (userDTO.getUsername() != null) user.setUsername(userDTO.getUsername());
        if (userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());
        if (userDTO.getProfilePicture() != null) user.setProfilePicture(userDTO.getProfilePicture());

        user = userRepository.save(user);

        return userMapper.toResponseDTO(user);
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);

    }

    @Override
    public UserResponseDTO updatePassword(Long id, String newPassword) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = existingUser.get();
        user.setPassword(passwordEncoder.encode(newPassword));

        user = userRepository.save(user);

        return userMapper.toResponseDTO(user);
    }


}
