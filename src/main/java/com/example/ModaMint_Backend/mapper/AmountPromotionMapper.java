package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.request.promotion.AmountPromotionRequest;
import com.example.ModaMint_Backend.dto.response.promotion.AmountPromotionResponse;
import com.example.ModaMint_Backend.entity.AmountPromotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AmountPromotionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "orders", ignore = true)
    AmountPromotion toAmountPromotion(AmountPromotionRequest request);

    AmountPromotionResponse toAmountPromotionResponse(AmountPromotion amountPromotion);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "orders", ignore = true)
    void updateAmountPromotion(AmountPromotionRequest request, @MappingTarget AmountPromotion amountPromotion);
}

