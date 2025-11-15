package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.request.product.ProductRequest;
import com.example.ModaMint_Backend.dto.response.product.ProductResponse;
import com.example.ModaMint_Backend.dto.response.product.ProductVariantResponse;
import com.example.ModaMint_Backend.entity.Product;

import com.example.ModaMint_Backend.entity.ProductVariant;
import org.mapstruct.Named;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "productVariants", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Product toProduct(ProductRequest request);

    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(source = "brand.name", target = "brandName")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "productVariants", target = "productVariants", qualifiedByName = "mapProductVariants")
    @Mapping(target = "price", expression = "java(getMinPriceFromVariants(product))")
    @Mapping(target = "quantity", expression = "java(getTotalQuantityFromVariants(product))")
    @Mapping(source = "images", target = "images")
    ProductResponse toProductResponse(Product product);

    default BigDecimal getMinPriceFromVariants(Product product) {
        try {
            if (product != null && product.getProductVariants() != null && !product.getProductVariants().isEmpty()) {
                return product.getProductVariants().stream()
                        .filter(variant -> variant != null && variant.getPrice() != null)
                        .map(variant -> variant.getPrice())
                        .min(BigDecimal::compareTo)
                        .orElse(BigDecimal.ZERO);
            }
        } catch (Exception e) {
            // Fall back to zero if any error
        }
        return BigDecimal.ZERO;
    }

    default Integer getTotalQuantityFromVariants(Product product) {
        try {
            if (product != null && product.getProductVariants() != null && !product.getProductVariants().isEmpty()) {
                return product.getProductVariants().stream()
                        .filter(variant -> variant != null && variant.getQuantity() != null)
                        .mapToInt(variant -> variant.getQuantity())
                        .sum();
            }
        } catch (Exception e) {
            // Fall back to zero if any error
        }
        return 0;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "productVariants", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    void updateProduct(ProductRequest request, @MappingTarget Product product);


    // Removed duplicate simple-mapping for price; using getMinPriceFromVariants(product) to compute price

    @Named("mapProductVariants")
    default List<ProductVariantResponse> mapProductVariants(Set<ProductVariant> productVariants) {
        if (productVariants == null || productVariants.isEmpty()) {
            return List.of();
        }
        return productVariants.stream()
                .map(v -> ProductVariantResponse.builder()
                        .id(v.getId())
                        .productId(v.getProduct() != null ? v.getProduct().getId() : null)
                        .size(v.getSize())
                        .color(v.getColor())
                        .price(v.getPrice())
                        .discount(v.getDiscount())
                        .quantity(v.getQuantity())
                        .additionalPrice(v.getAdditionalPrice())
                        .build())
                .collect(Collectors.toList());
    }
}
