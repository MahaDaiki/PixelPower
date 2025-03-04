package com.api.PixelPower.dto.request;

import lombok.Data;

@Data
public class ReviewRequestDTO {
    private String gameName;
    private int rating;
    private String comment;
    private Long userId;
}
