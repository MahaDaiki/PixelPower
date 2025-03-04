package com.api.PixelPower.mapper;

import com.api.PixelPower.dto.request.ReviewRequestDTO;
import com.api.PixelPower.dto.response.ReviewResponseDTO;
import com.api.PixelPower.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toEntity(ReviewRequestDTO reviewRequestDTO);

    ReviewResponseDTO toResponseDTO(Review review);
}
