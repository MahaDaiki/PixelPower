package com.api.PixelPower.mapper;

import com.api.PixelPower.dto.request.ReviewRequestDTO;
import com.api.PixelPower.dto.response.ReviewResponseDTO;
import com.api.PixelPower.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toEntity(ReviewRequestDTO reviewRequestDTO);
    @Mapping(source = "user", target = "user")
    ReviewResponseDTO toResponseDTO(Review review);
}
