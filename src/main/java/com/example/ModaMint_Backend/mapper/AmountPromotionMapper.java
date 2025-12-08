package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.request.promotion.AmountPromotionRequest;
import com.example.ModaMint_Backend.dto.response.promotion.AmountPromotionResponse;
import com.example.ModaMint_Backend.entity.AmountPromotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface AmountPromotionMapper {
    
    @Mapping(target = "promotionId", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "discount", source = "discountAmount", qualifiedByName = "bigDecimalToDouble")
    @Mapping(target = "effective", source = "startAt")
    @Mapping(target = "expiration", source = "endAt")
    AmountPromotion toAmountPromotion(AmountPromotionRequest request);

    @Mapping(target = "id", source = "promotionId")
    @Mapping(target = "discountAmount", source = "discount", qualifiedByName = "doubleToBigDecimal")
    @Mapping(target = "startAt", source = "effective")
    @Mapping(target = "endAt", source = "expiration")
    AmountPromotionResponse toAmountPromotionResponse(AmountPromotion amountPromotion);

    @Mapping(target = "promotionId", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "discount", source = "discountAmount", qualifiedByName = "bigDecimalToDouble")
    @Mapping(target = "effective", source = "startAt")
    @Mapping(target = "expiration", source = "endAt")
    void updateAmountPromotion(AmountPromotionRequest request, @MappingTarget AmountPromotion amountPromotion);

    @Named("bigDecimalToDouble")
    default double bigDecimalToDouble(BigDecimal value) {
        return value != null ? value.doubleValue() : 0.0;
    }

    @Named("doubleToBigDecimal")
    default BigDecimal doubleToBigDecimal(double value) {
        return BigDecimal.valueOf(value);
    }
}

