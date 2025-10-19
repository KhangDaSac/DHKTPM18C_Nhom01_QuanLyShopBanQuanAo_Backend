package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.request.brand.BrandRequest;
import com.example.ModaMint_Backend.dto.response.brand.BrandResponse;
import com.example.ModaMint_Backend.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Brand toBrand(BrandRequest request);

    @Mapping(target = "productCount", expression = "java(brand.getProducts() != null ? brand.getProducts().size() : 0)")
    BrandResponse toBrandResponse(Brand brand);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    void updateBrand(BrandRequest request, @MappingTarget Brand brand);
}
