package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.request.order.OrderRequest;
import com.example.ModaMint_Backend.dto.response.order.OrderResponse;
import com.example.ModaMint_Backend.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "percentagePromotion", ignore = true)
    @Mapping(target = "amountPromotion", ignore = true)
    @Mapping(target = "shippingAddress", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "orderStatusHistories", ignore = true)
    @Mapping(target = "shipments", ignore = true)
    @Mapping(target = "payment", ignore = true)
    Order toOrder(OrderRequest request);

    OrderResponse toOrderResponse(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "percentagePromotion", ignore = true)
    @Mapping(target = "amountPromotion", ignore = true)
    @Mapping(target = "shippingAddress", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "orderStatusHistories", ignore = true)
    @Mapping(target = "shipments", ignore = true)
    @Mapping(target = "payment", ignore = true)
    void updateOrder(OrderRequest request, @MappingTarget Order order);
}
