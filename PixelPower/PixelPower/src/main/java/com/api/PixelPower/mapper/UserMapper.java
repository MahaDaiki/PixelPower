package com.api.PixelPower.mapper;

import com.api.PixelPower.dto.UserDTO;
import com.api.PixelPower.dto.UserResponseDTO;
import com.api.PixelPower.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);

    UserResponseDTO toResponseDTO(User user);
}
