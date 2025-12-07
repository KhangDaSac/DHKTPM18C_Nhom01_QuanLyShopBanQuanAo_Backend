package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.request.order.OrderRequest;
import com.example.ModaMint_Backend.dto.response.order.OrderResponse;
import com.example.ModaMint_Backend.dto.response.orderitem.OrderItemResponse;
import com.example.ModaMint_Backend.entity.Order;
import com.example.ModaMint_Backend.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "percentPromotion", ignore = true)
    @Mapping(target = "amountPromotion", ignore = true)
    @Mapping(target = "percentPromotionId", ignore = true)
    @Mapping(target = "amountPromotionId", ignore = true)
    @Mapping(target = "shippingAddress", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "orderStatusHistories", ignore = true)
    @Mapping(target = "shipments", ignore = true)
    @Mapping(target = "payment", ignore = true)
    Order toOrder(OrderRequest request);

    @Mapping(target = "promotionId", ignore = true)
    @Mapping(target = "orderItems", source = "orderItems")
    @Mapping(target = "totalAmount", source = "subTotal")
    @Mapping(target = "shippingAddress", source = "shippingAddress")
    @Mapping(target = "shippingFee", expression = "java(new java.math.BigDecimal(30000))")
    OrderResponse toOrderResponse(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "percentPromotion", ignore = true)
    @Mapping(target = "amountPromotion", ignore = true)
    @Mapping(target = "percentPromotionId", ignore = true)
    @Mapping(target = "amountPromotionId", ignore = true)
    @Mapping(target = "shippingAddress", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "orderStatusHistories", ignore = true)
    @Mapping(target = "shipments", ignore = true)
    @Mapping(target = "payment", ignore = true)
    void updateOrder(OrderRequest request, @MappingTarget Order order);


    @Mapping(target = "lineTotal", expression = "java(orderItem.getLineTotal())")
    @Mapping(target = "productVariantName", source = "productVariant.product.name") 
    @Mapping(target = "size", source = "productVariant.size")
    @Mapping(target = "color", source = "productVariant.color")
    @Mapping(target = "productVariantImage", source = "productVariant.image")
    @Mapping(target = "productId", source = "productVariant.productId")
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
}
