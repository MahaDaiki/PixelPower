package com.api.PixelPower.service.serviceInt;

import com.api.PixelPower.dto.AuthTokenResponseDTO;
import com.api.PixelPower.dto.LoginRequestDTO;
import com.api.PixelPower.dto.UserDTO;
import com.api.PixelPower.dto.UserResponseDTO;

import java.util.*;

public interface UserServiceInt {
    UserResponseDTO register(UserDTO user);
    AuthTokenResponseDTO login(LoginRequestDTO loginRequest);

    List<UserResponseDTO> getAllUsers();
    Optional<UserResponseDTO> getUserById(Long id);
    UserResponseDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    UserResponseDTO updatePassword(Long id, String newPassword);
}
