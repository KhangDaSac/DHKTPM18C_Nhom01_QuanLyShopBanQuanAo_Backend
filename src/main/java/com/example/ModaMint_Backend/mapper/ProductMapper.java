package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.request.product.ProductRequest;
import com.example.ModaMint_Backend.dto.response.product.ProductResponse;
import com.example.ModaMint_Backend.dto.response.product.ProductVariantResponse;
import com.example.ModaMint_Backend.entity.Product;
import com.example.ModaMint_Backend.entity.ProductImage;
import com.example.ModaMint_Backend.entity.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

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
    @Mapping(target = "productImages", ignore = true)
    Product toProduct(ProductRequest request);

    @Mapping(source = "brand.name", target = "brandName")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "productImages", target = "images", qualifiedByName = "productImagesToUrls")
    @Mapping(source = "productVariants", target = "price", qualifiedByName = "getFirstVariantPrice")
    @Mapping(source = "productVariants", target = "productVariants", qualifiedByName = "mapProductVariants")
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "productVariants", ignore = true)
    @Mapping(target = "productImages", ignore = true)
    void updateProduct(ProductRequest request, @MappingTarget Product product);
    
    @Named("productImagesToUrls")
    default List<String> productImagesToUrls(Set<ProductImage> productImages) {
        if (productImages == null || productImages.isEmpty()) {
            return List.of();
        }
        return productImages.stream()
                .map(ProductImage::getUrl)
                .collect(Collectors.toList());
    }
    
    @Named("getFirstVariantPrice")
    default BigDecimal getFirstVariantPrice(Set<ProductVariant> productVariants) {
        if (productVariants == null || productVariants.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return productVariants.stream()
                .findFirst()
                .map(ProductVariant::getPrice)
                .orElse(BigDecimal.ZERO);
    }
    
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
