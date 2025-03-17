package com.api.PixelPower.dto.response;

import com.api.PixelPower.dto.UserDTO;
import lombok.Data;

@Data
public class ReviewResponseDTO {
    private Long id;
    private String gameName;
    private int rating;
    private String comment;
    private UserDTO user;
    private String createdAt;
}
