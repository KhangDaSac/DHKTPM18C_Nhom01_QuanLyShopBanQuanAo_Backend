package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.review.ReviewRequest;
import com.example.ModaMint_Backend.dto.response.review.ReviewResponse;
import com.example.ModaMint_Backend.entity.Review;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.mapper.ReviewMapper;
import com.example.ModaMint_Backend.repository.ReviewRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewService {
    ReviewRepository reviewRepository;
    ReviewMapper reviewMapper;

    public ReviewResponse createReview(ReviewRequest request) {
        Review review = reviewMapper.toReview(request);
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toReviewResponse(savedReview);
    }

    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(reviewMapper::toReviewResponse)
                .toList();
    }

    public ReviewResponse getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
        return reviewMapper.toReviewResponse(review);
    }

    public Page<ReviewResponse> getReviewsWithPagination(Pageable pageable) {
        Page<Review> reviewPage = reviewRepository.findAll(pageable);
        return reviewPage.map(reviewMapper::toReviewResponse);
    }

    public ReviewResponse updateReview(Long id, ReviewRequest request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        reviewMapper.updateReview(request, review);
        Review updatedReview = reviewRepository.save(review);
        return reviewMapper.toReviewResponse(updatedReview);
    }

    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new AppException(ErrorCode.REVIEW_NOT_FOUND);
        }
        reviewRepository.deleteById(id);
    }

    public List<ReviewResponse> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId)
                .stream()
                .map(reviewMapper::toReviewResponse)
                .toList();
    }

    public List<ReviewResponse> getReviewsByCustomerId(String customerId) {
        return reviewRepository.findByCustomerId(customerId)
                .stream()
                .map(reviewMapper::toReviewResponse)
                .toList();
    }

    public long getTotalReviewCount() {
        return reviewRepository.count();
    }

    public double getAverageRatingByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        if (reviews.isEmpty()) {
            return 0.0;
        }
        return reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }

    public List<ReviewResponse> getLatest10Reviews() {
        List<Review> latestReviews = reviewRepository.findTop10ByOrderByCreateAtDesc();
        return latestReviews.stream()
                .map(reviewMapper::toReviewResponse)
                .toList();
    }
}
