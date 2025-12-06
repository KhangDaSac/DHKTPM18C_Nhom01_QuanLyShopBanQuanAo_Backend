package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.request.productvariant.ProductVariantRequest;
import com.example.ModaMint_Backend.dto.response.productvariant.ProductVariantResponse;
import com.example.ModaMint_Backend.entity.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "image", source = "imageUrl")
    ProductVariant toProductVariant(ProductVariantRequest request);

    ProductVariantResponse toProductVariantResponse(ProductVariant productVariant);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "image", source = "imageUrl")
    void updateProductVariant(ProductVariantRequest request, @MappingTarget ProductVariant productVariant);
}
