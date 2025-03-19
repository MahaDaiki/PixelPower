package com.api.PixelPower.service.serviceImpl;

import com.api.PixelPower.dto.UserDTO;
import com.api.PixelPower.dto.request.ReviewRequestDTO;
import com.api.PixelPower.dto.response.ReviewResponseDTO;
import com.api.PixelPower.entity.Review;
import com.api.PixelPower.entity.User;
import com.api.PixelPower.exception.ResourceNotFoundException;
import com.api.PixelPower.mapper.ReviewMapper;
import com.api.PixelPower.repository.ReviewRepository;
import com.api.PixelPower.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private User testUser;
    private Review testReview;
    private ReviewRequestDTO testRequestDTO;
    private ReviewResponseDTO testResponseDTO;
    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");

        testUserDTO = new UserDTO();
        testUserDTO.setUsername("testuser");

        testReview = new Review();
        testReview.setId(1L);
        testReview.setGameName("Minecraft");
        testReview.setRating(5);
        testReview.setComment("Great game!");
        testReview.setUser(testUser);

        testRequestDTO = new ReviewRequestDTO();
        testRequestDTO.setGameName("Minecraft");
        testRequestDTO.setRating(5);
        testRequestDTO.setComment("Great game!");

        testResponseDTO = new ReviewResponseDTO();
        testResponseDTO.setId(1L);
        testResponseDTO.setUser(testUserDTO);
        testResponseDTO.setGameName("Minecraft");
        testResponseDTO.setRating(5);
        testResponseDTO.setComment("Great game!");
        testResponseDTO.setCreatedAt("2025-03-19T12:00:00");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void createReview_ShouldCreateAndReturnReview() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(reviewMapper.toEntity(any(ReviewRequestDTO.class))).thenReturn(testReview);
        when(reviewRepository.save(any(Review.class))).thenReturn(testReview);
        when(reviewMapper.toResponseDTO(any(Review.class))).thenReturn(testResponseDTO);

        ReviewResponseDTO result = reviewService.createReview(testRequestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Minecraft", result.getGameName());
        assertEquals(5, result.getRating());
        assertEquals("testuser", result.getUser().getUsername());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void createReview_WithNonExistentUser_ShouldThrowException() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            reviewService.createReview(testRequestDTO);
        });
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void updateReview_ShouldUpdateAndReturnReview() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(testReview));
        when(reviewRepository.save(any(Review.class))).thenReturn(testReview);
        when(reviewMapper.toResponseDTO(any(Review.class))).thenReturn(testResponseDTO);


        ReviewRequestDTO updateRequestDTO = new ReviewRequestDTO();
        updateRequestDTO.setGameName("Updated Game");
        updateRequestDTO.setRating(4);
        updateRequestDTO.setComment("Updated comment");

        ReviewResponseDTO result = reviewService.updateReview(1L, updateRequestDTO);

        assertNotNull(result);
        verify(reviewRepository, times(1)).findById(1L);
        verify(reviewRepository, times(1)).save(any(Review.class));

        assertEquals("Updated Game", testReview.getGameName());
        assertEquals(4, testReview.getRating());
        assertEquals("Updated comment", testReview.getComment());
    }

    @Test
    void updateReview_WithNonExistentReview_ShouldThrowException() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            reviewService.updateReview(1L, testRequestDTO);
        });
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void deleteReview_ShouldDeleteReview() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(testReview));
        doNothing().when(reviewRepository).delete(any(Review.class));

        reviewService.deleteReview(1L);

        verify(reviewRepository, times(1)).findById(1L);
        verify(reviewRepository, times(1)).delete(testReview);
    }

    @Test
    void deleteReview_WithNonExistentReview_ShouldThrowException() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            reviewService.deleteReview(1L);
        });
        verify(reviewRepository, never()).delete(any(Review.class));
    }

    @Test
    void getAllReviews_ShouldReturnAllReviews() {
        List<Review> reviewList = Arrays.asList(testReview);
        when(reviewRepository.findAll()).thenReturn(reviewList);
        when(reviewMapper.toResponseDTO(any(Review.class))).thenReturn(testResponseDTO);

        List<ReviewResponseDTO> result = reviewService.getAllReviews();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Minecraft", result.get(0).getGameName());
        assertEquals("testuser", result.get(0).getUser().getUsername());
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void getReviewById_ShouldReturnReviewWithMatchingId() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(testReview));
        when(reviewMapper.toResponseDTO(any(Review.class))).thenReturn(testResponseDTO);

        ReviewResponseDTO result = reviewService.getReviewById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Minecraft", result.getGameName());
        assertEquals("testuser", result.getUser().getUsername());
        verify(reviewRepository, times(1)).findById(1L);
    }

    @Test
    void getReviewById_WithNonExistentReview_ShouldThrowException() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            reviewService.getReviewById(1L);
        });
    }

    @Test
    void getReviewsByUserId_ShouldReturnReviewsForSpecificUser() {
        List<Review> reviewList = Arrays.asList(testReview);
        when(reviewRepository.findByUserId(anyLong())).thenReturn(reviewList);
        when(reviewMapper.toResponseDTO(any(Review.class))).thenReturn(testResponseDTO);

        List<ReviewResponseDTO> result = reviewService.getReviewsByUserId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Minecraft", result.get(0).getGameName());
        assertEquals("testuser", result.get(0).getUser().getUsername());
        verify(reviewRepository, times(1)).findByUserId(1L);
    }

    @Test
    void getReviewsByGameName_ShouldReturnReviewsForSpecificGame() {
        List<Review> reviewList = Arrays.asList(testReview);
        when(reviewRepository.findByGameName(anyString())).thenReturn(reviewList);
        when(reviewMapper.toResponseDTO(any(Review.class))).thenReturn(testResponseDTO);

        List<ReviewResponseDTO> result = reviewService.getReviewsByGameName("Minecraft");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Minecraft", result.get(0).getGameName());
        assertEquals("testuser", result.get(0).getUser().getUsername());
        verify(reviewRepository, times(1)).findByGameName("Minecraft");
    }
}