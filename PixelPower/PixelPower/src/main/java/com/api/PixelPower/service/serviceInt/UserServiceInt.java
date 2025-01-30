package com.api.PixelPower.service.serviceInt;

import com.api.PixelPower.dto.UserDTO;
import com.api.PixelPower.dto.UserResponseDTO;

public interface UserServiceInt {
    UserResponseDTO register(UserDTO user);
//    UserResponseDTO login(LoginDTO loginDTO);
}
