package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.request.category.CategoryRequest;
import com.example.ModaMint_Backend.dto.response.category.CategoryResponse;
import com.example.ModaMint_Backend.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toCategory(CategoryRequest request);

    @Mapping(target = "productCount", expression = "java(category.getProducts() != null ? category.getProducts().size() : 0)")
    @Mapping(target = "image", source = "image")
    CategoryResponse toCategoryResponse(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    void updateCategory(CategoryRequest request, @MappingTarget Category category);
}
