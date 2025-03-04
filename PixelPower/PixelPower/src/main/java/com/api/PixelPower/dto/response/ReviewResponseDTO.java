package com.api.PixelPower.dto.response;

import lombok.Data;

@Data
public class ReviewResponseDTO {
    private Long id;
    private String gameName;
    private int rating;
    private String comment;
    private String userId;
    private String createdAt;
}
