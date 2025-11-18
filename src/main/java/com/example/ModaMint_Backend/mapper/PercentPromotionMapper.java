package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.request.promotion.PercentPromotionRequest;
import com.example.ModaMint_Backend.dto.response.promotion.PercentPromotionResponse;
import com.example.ModaMint_Backend.entity.PercentPromotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface PercentPromotionMapper {
    @Mapping(target = "promotionId", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "percent", source = "discountPercent", qualifiedByName = "bigDecimalToDouble")
    @Mapping(target = "maxDiscount", source = "maxDiscount", qualifiedByName = "bigDecimalToDouble")
    @Mapping(target = "effective", source = "startAt")
    @Mapping(target = "expiration", source = "endAt")
    PercentPromotion toPercentPromotion(PercentPromotionRequest request);

    @Mapping(target = "id", source = "promotionId")
    @Mapping(target = "discountPercent", source = "percent", qualifiedByName = "doubleToBigDecimal")
    @Mapping(target = "maxDiscount", source = "maxDiscount", qualifiedByName = "doubleToBigDecimal")
    @Mapping(target = "startAt", source = "effective")
    @Mapping(target = "endAt", source = "expiration")
    PercentPromotionResponse toPercentPromotionResponse(PercentPromotion percentPromotion);

    @Mapping(target = "promotionId", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "percent", source = "discountPercent", qualifiedByName = "bigDecimalToDouble")
    @Mapping(target = "maxDiscount", source = "maxDiscount", qualifiedByName = "bigDecimalToDouble")
    @Mapping(target = "effective", source = "startAt")
    @Mapping(target = "expiration", source = "endAt")
    void updatePercentagePromotion(PercentPromotionRequest request, @MappingTarget PercentPromotion percentPromotion);

    @Named("bigDecimalToDouble")
    default double bigDecimalToDouble(BigDecimal value) {
        return value != null ? value.doubleValue() : 0.0;
    }

    @Named("doubleToBigDecimal")
    default BigDecimal doubleToBigDecimal(double value) {
        return BigDecimal.valueOf(value);
    }
}

