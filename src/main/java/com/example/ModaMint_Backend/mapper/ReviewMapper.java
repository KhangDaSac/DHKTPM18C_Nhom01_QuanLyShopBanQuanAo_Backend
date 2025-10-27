package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.request.review.ReviewRequest;
import com.example.ModaMint_Backend.dto.response.review.ReviewResponse;
import com.example.ModaMint_Backend.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "orderItem", ignore = true)
    Review toReview(ReviewRequest request);

    ReviewResponse toReviewResponse(Review review);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "orderItem", ignore = true)
    void updateReview(ReviewRequest request, @MappingTarget Review review);
}
