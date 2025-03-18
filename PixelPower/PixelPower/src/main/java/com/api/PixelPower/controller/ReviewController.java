package com.api.PixelPower.controller;


import com.api.PixelPower.dto.request.ReviewRequestDTO;
import com.api.PixelPower.dto.response.ReviewResponseDTO;
import com.api.PixelPower.service.serviceInt.ReviewServiceInt;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
public class ReviewController {
    private final ReviewServiceInt reviewService;

    @PostMapping
    public ReviewResponseDTO createReview(@RequestBody ReviewRequestDTO reviewRequestDTO) {
        return reviewService.createReview(reviewRequestDTO);
    }

    @PutMapping("/{id}")
    public ReviewResponseDTO updateReview(@PathVariable Long id, @RequestBody ReviewRequestDTO reviewRequestDTO) {
        return reviewService.updateReview(id, reviewRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }

    @GetMapping
    public List<ReviewResponseDTO> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public ReviewResponseDTO getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    @GetMapping("/user/{userId}")
    public List<ReviewResponseDTO> getReviewsByUserId(@PathVariable Long userId) {
        return reviewService.getReviewsByUserId(userId);
    }

    @GetMapping("/game/{gameName}")
    public List<ReviewResponseDTO> getReviewsByGameName(@PathVariable String gameName) {
        return reviewService.getReviewsByGameName(gameName);
    }
}
