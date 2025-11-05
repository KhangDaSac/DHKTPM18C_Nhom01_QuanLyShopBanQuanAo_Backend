package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.request.promotion.PercentagePromotionRequest;
import com.example.ModaMint_Backend.dto.response.promotion.PercentagePromotionResponse;
import com.example.ModaMint_Backend.entity.PercentagePromotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PercentagePromotionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "orders", ignore = true)
    PercentagePromotion toPercentagePromotion(PercentagePromotionRequest request);

    PercentagePromotionResponse toPercentagePromotionResponse(PercentagePromotion percentagePromotion);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "orders", ignore = true)
    void updatePercentagePromotion(PercentagePromotionRequest request, @MappingTarget PercentagePromotion percentagePromotion);
}

