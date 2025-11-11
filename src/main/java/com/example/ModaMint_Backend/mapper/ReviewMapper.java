package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.request.review.ReviewRequest;
import com.example.ModaMint_Backend.dto.response.review.ReviewResponse;
import com.example.ModaMint_Backend.entity.Product;
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

    @Mapping(target = "firstName", source = "customer.user.firstName")
    @Mapping(target = "lastName", source = "customer.user.lastName")
    @Mapping(target = "image", source = "customer.user.image")
    @Mapping(target = "productImage", expression = "java(getFirstProductImage(review.getProduct()))")
    ReviewResponse toReviewResponse(Review review);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "orderItem", ignore = true)
    @Mapping(source = "images", target = "images")
    void updateReview(ReviewRequest request, @MappingTarget Review review);

    default String getFirstProductImage(Product product) {
        if (product == null || product.getImages() == null || product.getImages().isEmpty()) {
            return null; // Hoặc trả về một URL ảnh placeholder mặc định
        }

        // Lấy phần tử đầu tiên tìm thấy từ Set
        return product.getImages().stream().findFirst().orElse(null);
    }
}
