package com.api.PixelPower.mapper;

import com.api.PixelPower.dto.ReviewDTO;
import com.api.PixelPower.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
//    @Mapping(source = "user.id", target = "userId")
//    @Mapping(source = "gameComparison.id", target = "gameComparisonId")
    ReviewDTO toDTO(Review review);

//    @Mapping(source = "userId", target = "user.id")
//    @Mapping(source = "gameComparisonId", target = "gameComparison.id")
    Review toEntity(ReviewDTO reviewDTO);
}
