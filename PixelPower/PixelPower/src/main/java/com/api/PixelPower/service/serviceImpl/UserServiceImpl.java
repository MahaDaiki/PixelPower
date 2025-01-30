package com.api.PixelPower.service.serviceImpl;

import com.api.PixelPower.dto.UserDTO;
import com.api.PixelPower.dto.UserResponseDTO;
import com.api.PixelPower.entity.Role;
import com.api.PixelPower.entity.User;
import com.api.PixelPower.exception.EmailAlreadyExistsException;
import com.api.PixelPower.mapper.UserMapper;
import com.api.PixelPower.repository.UserRepository;
import com.api.PixelPower.service.serviceInt.UserServiceInt;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceInt {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

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
}
