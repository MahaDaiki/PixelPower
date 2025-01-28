package com.api.PixelPower.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private Long id;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
    private Long userId;
    private Long gameComparisonId;
}
