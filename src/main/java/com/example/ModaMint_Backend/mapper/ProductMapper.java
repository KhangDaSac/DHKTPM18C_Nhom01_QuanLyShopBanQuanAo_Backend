package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.request.product.ProductRequest;
import com.example.ModaMint_Backend.dto.response.product.ProductResponse;
import com.example.ModaMint_Backend.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

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
    @Mapping(target = "reviews", ignore = true)
    void updateProduct(ProductRequest request, @MappingTarget Product product);
}
