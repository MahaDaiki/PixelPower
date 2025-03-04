package com.api.PixelPower.service.serviceImpl;

import com.api.PixelPower.dto.request.ReviewRequestDTO;
import com.api.PixelPower.dto.response.ReviewResponseDTO;
import com.api.PixelPower.entity.Review;
import com.api.PixelPower.entity.User;
import com.api.PixelPower.exception.ResourceNotFoundException;
import com.api.PixelPower.mapper.ReviewMapper;
import com.api.PixelPower.repository.ReviewRepository;
import com.api.PixelPower.repository.UserRepository;
import com.api.PixelPower.service.serviceInt.ReviewServiceInt;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewServiceInt {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;

    @Override
    public ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Review review = reviewMapper.toEntity(reviewRequestDTO);
        review.setUser(user);
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toResponseDTO(savedReview);
    }

    @Override
    public ReviewResponseDTO updateReview(Long id, ReviewRequestDTO reviewRequestDTO) {
        Review existingReview = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
        existingReview.setGameName(reviewRequestDTO.getGameName());
        existingReview.setRating(reviewRequestDTO.getRating());
        existingReview.setComment(reviewRequestDTO.getComment());
        Review updatedReview = reviewRepository.save(existingReview);
        return reviewMapper.toResponseDTO(updatedReview);
    }

    @Override
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
        reviewRepository.delete(review);
    }

    @Override
    public List<ReviewResponseDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponseDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
        return reviewMapper.toResponseDTO(review);
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByUserId(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        return reviews.stream()
                .map(reviewMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
