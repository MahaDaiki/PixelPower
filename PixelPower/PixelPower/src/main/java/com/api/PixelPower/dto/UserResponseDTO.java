package com.api.PixelPower.dto;

import com.api.PixelPower.entity.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private String profilePicture;
    private LocalDateTime createdAt;
//    private String token;
}
