package com.api.PixelPower.service.serviceInt;

import com.api.PixelPower.dto.response.AuthTokenResponseDTO;
import com.api.PixelPower.dto.request.LoginRequestDTO;
import com.api.PixelPower.dto.UserDTO;
import com.api.PixelPower.dto.response.UserResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

public interface UserServiceInt {
    UserResponseDTO register(UserDTO user);
    AuthTokenResponseDTO login(LoginRequestDTO loginRequest);


    UserResponseDTO getAuthenticatedUser();
    List<UserResponseDTO> getAllUsers();
    Optional<UserResponseDTO> getUserById(Long id);
    UserResponseDTO updateAuthenticatedUser(UserDTO userDTO);
    UserResponseDTO updateProfilePicture(MultipartFile profilePicture);
    void deleteUser(Long id);
    UserResponseDTO updateAuthenticatedPassword(String newPassword);
}
