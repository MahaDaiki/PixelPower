package com.api.PixelPower.service.serviceInt;

import com.api.PixelPower.dto.AuthTokenResponseDTO;
import com.api.PixelPower.dto.LoginRequestDTO;
import com.api.PixelPower.dto.UserDTO;
import com.api.PixelPower.dto.UserResponseDTO;

public interface UserServiceInt {
    UserResponseDTO register(UserDTO user);
    AuthTokenResponseDTO login(LoginRequestDTO loginRequest);
}
