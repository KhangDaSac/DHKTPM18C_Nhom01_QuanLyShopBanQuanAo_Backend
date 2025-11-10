package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.review.ReviewRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.review.ReviewResponse;
import com.example.ModaMint_Backend.service.ReviewService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewController {
    ReviewService reviewService;

    @PostMapping
    public ApiResponse<ReviewResponse> createReview(@RequestBody @Valid ReviewRequest request) {
        return ApiResponse.<ReviewResponse>builder()
                .result(reviewService.createReview(request))
                .message("Tạo đánh giá mới thành công")
                .build();
    }

    @GetMapping
    public ApiResponse<List<ReviewResponse>> getAllReviews() {
        return ApiResponse.<List<ReviewResponse>>builder()
                .result(reviewService.getAllReviews())
                .message("Lấy danh sách đánh giá thành công")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ReviewResponse> getReviewById(@PathVariable Long id) {
        return ApiResponse.<ReviewResponse>builder()
                .result(reviewService.getReviewById(id))
                .message("Lấy thông tin đánh giá thành công")
                .build();
    }

    @GetMapping("/paginated")
    public ApiResponse<Page<ReviewResponse>> getReviewsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ApiResponse.<Page<ReviewResponse>>builder()
                .result(reviewService.getReviewsWithPagination(pageable))
                .message("Lấy danh sách đánh giá phân trang thành công")
                .build();
    }

    @GetMapping("/product/{productId}")
    public ApiResponse<List<ReviewResponse>> getReviewsByProductId(@PathVariable Long productId) {
        return ApiResponse.<List<ReviewResponse>>builder()
                .result(reviewService.getReviewsByProductId(productId))
                .message("Lấy danh sách đánh giá theo sản phẩm thành công")
                .build();
    }

    @GetMapping("/customer/{customerId}")
    public ApiResponse<List<ReviewResponse>> getReviewsByCustomerId(@PathVariable String customerId) {
        return ApiResponse.<List<ReviewResponse>>builder()
                .result(reviewService.getReviewsByCustomerId(customerId))
                .message("Lấy danh sách đánh giá theo khách hàng thành công")
                .build();
    }

    @GetMapping("/product/{productId}/average-rating")
    public ApiResponse<Double> getAverageRatingByProductId(@PathVariable Long productId) {
        return ApiResponse.<Double>builder()
                .result(reviewService.getAverageRatingByProductId(productId))
                .message("Lấy điểm đánh giá trung bình thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ReviewResponse> updateReview(
            @PathVariable Long id,
            @RequestBody @Valid ReviewRequest request) {
        return ApiResponse.<ReviewResponse>builder()
                .result(reviewService.updateReview(id, request))
                .message("Cập nhật đánh giá thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ApiResponse.<String>builder()
                .result("Đánh giá đã được xóa")
                .message("Xóa đánh giá thành công")
                .build();
    }

    @GetMapping("/count")
    public ApiResponse<Long> getTotalReviewCount() {
        return ApiResponse.<Long>builder()
                .result(reviewService.getTotalReviewCount())
                .message("Lấy tổng số lượng đánh giá thành công")
                .build();
    }
    @GetMapping("/latest")
    public ApiResponse<List<ReviewResponse>> getLatest10Reviews() {
        return ApiResponse.<List<ReviewResponse>>builder()
                .result(reviewService.getLatest10Reviews())
                .message("Lấy 10 đánh giá mới nhất thành công")
                .build();
    }
}
