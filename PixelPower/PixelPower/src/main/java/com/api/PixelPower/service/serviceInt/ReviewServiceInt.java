package com.api.PixelPower.service.serviceInt;
import com.api.PixelPower.dto.request.ReviewRequestDTO;
import com.api.PixelPower.dto.response.ReviewResponseDTO;

import java.util.List;

public interface ReviewServiceInt {
    ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO);
    ReviewResponseDTO updateReview(Long id, ReviewRequestDTO reviewRequestDTO);
    void deleteReview(Long id);
    List<ReviewResponseDTO> getAllReviews();
    ReviewResponseDTO getReviewById(Long id);
    List<ReviewResponseDTO> getReviewsByUserId(Long userId);
    List<ReviewResponseDTO> getReviewsByGameName(String gameName);
}
