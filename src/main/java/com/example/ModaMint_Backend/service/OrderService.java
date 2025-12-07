package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.order.OrderRequest;
import com.example.ModaMint_Backend.dto.response.cart.CartItemDto;
import com.example.ModaMint_Backend.dto.response.checkout.CheckoutResponse;
import com.example.ModaMint_Backend.dto.response.customer.AddressResponse;
import com.example.ModaMint_Backend.dto.response.order.OrderResponse;
import com.example.ModaMint_Backend.entity.Order;
import com.example.ModaMint_Backend.entity.OrderStatusHistory;
import com.example.ModaMint_Backend.enums.OrderStatus;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.mapper.OrderMapper;
import com.example.ModaMint_Backend.repository.OrderRepository;
import com.example.ModaMint_Backend.repository.OrderStatusHistoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderService {
    OrderRepository orderRepository;
    OrderStatusHistoryRepository orderStatusHistoryRepository;
    OrderMapper orderMapper;
    EmailService emailService;

    public OrderResponse createOrder(OrderRequest request) {
        Order order = orderMapper.toOrder(request);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(savedOrder);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return orderMapper.toOrderResponse(order);
    }

    public Page<OrderResponse> getOrdersWithPagination(Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return orderPage.map(orderMapper::toOrderResponse);
    }

    public OrderResponse updateOrder(Long id, OrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        orderMapper.updateOrder(request, order);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(updatedOrder);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }
        orderRepository.deleteById(id);
    }

    public List<OrderResponse> getOrdersByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    public List<OrderResponse> getOrdersByStatus(String status) {
        OrderStatus orderStatus = OrderStatus.valueOf(status);
        return orderRepository.findByOrderStatus(orderStatus)
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    public void updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        order.setOrderStatus(status);
        orderRepository.save(order);
    }

    public void cancelOrder(Long id, String customerId, String cancelReason) {
        log.info("Attempting to cancel order {} by customer {} with reason: {}", id, customerId, cancelReason);
        
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (!order.getCustomerId().equals(customerId)) {
            log.error("Customer {} attempted to cancel order {} that belongs to {}", 
                customerId, id, order.getCustomerId());
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            log.error("Cannot cancel order {} with status {}", id, order.getOrderStatus());
            throw new AppException(ErrorCode.CANNOT_CANCEL_ORDER);
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        
        // Lưu lịch sử hủy đơn với lý do
        OrderStatusHistory history = OrderStatusHistory.builder()
                .order(order)
                .orderStatus(OrderStatus.CANCELLED)
                .message(cancelReason)
                .build();
        orderStatusHistoryRepository.save(history);
        
        log.info("Order {} cancelled successfully by customer {}", id, customerId);
    }

    public long getTotalOrderCount() {
        return orderRepository.count();
    }

    public OrderResponse getOrderDetailById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return orderMapper.toOrderResponse(order);
    }

    public void sendOrderConfirmationEmailById(Long orderId) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
            CheckoutResponse response = buildCheckoutResponseFromOrder(order);
            String recipientEmail = order.getCustomer().getUser() != null 
                ? order.getCustomer().getUser().getEmail() 
                : order.getCustomer().getEmail();
            emailService.sendOrderConfirmationEmail(response, recipientEmail);
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }

    private CheckoutResponse buildCheckoutResponseFromOrder(Order order) {
        OrderResponse orderResponse = orderMapper.toOrderResponse(order);
        com.example.ModaMint_Backend.dto.response.customer.AddressResponse addressResponse = null;
        if (order.getShippingAddress() != null) {
            addressResponse = AddressResponse.builder()
                    .id(order.getShippingAddress().getId())
                    .customerId(order.getShippingAddress().getCustomer().getCustomerId())
                    .city(order.getShippingAddress().getCity())
                    .district(order.getShippingAddress().getDistrict())
                    .ward(order.getShippingAddress().getWard())
                    .addressDetail(order.getShippingAddress().getAddressDetail())
                    .fullAddress(order.getShippingAddress().getCity() + ", " + 
                                order.getShippingAddress().getDistrict() + ", " + 
                                order.getShippingAddress().getWard() + ", " + 
                                order.getShippingAddress().getAddressDetail())
                    .build();
        }

        List<CartItemDto> cartItems = new java.util.ArrayList<>();
        if (orderResponse.getOrderItems() != null) {
            for (var orderItem : orderResponse.getOrderItems()) {
                cartItems.add(CartItemDto.builder()
                        .variantId(orderItem.getProductVariantId())
                        .productId(orderItem.getProductId())
                        .productName(orderItem.getProductVariantName())
                        .size(orderItem.getSize())
                        .color(orderItem.getColor())
                        .image(orderItem.getProductVariantImage())
                        .unitPrice(orderItem.getUnitPrice() != null ? orderItem.getUnitPrice().longValue() : 0L)
                        .quantity(orderItem.getQuantity())
                        .totalPrice(orderItem.getLineTotal() != null ? orderItem.getLineTotal().longValue() : 0L)
                        .build());
            }
        }
        
        return CheckoutResponse.builder()
                .orderId(order.getId())
                .orderCode(order.getOrderCode())
                .customerId(order.getCustomer().getCustomerId())
                .customerName(order.getCustomer().getUser() != null 
                    ? order.getCustomer().getUser().getFirstName() + " " + order.getCustomer().getUser().getLastName()
                    : order.getCustomer().getName())
                .customerEmail(order.getCustomer().getUser() != null 
                    ? order.getCustomer().getUser().getEmail() 
                    : order.getCustomer().getEmail())
                .customerPhone(order.getPhone())
                .shippingAddress(addressResponse)
                .orderItems(cartItems)
                .subtotal(order.getTotalAmount().subtract(BigDecimal.valueOf(30000)).add(order.getPromotionValue() != null ? order.getPromotionValue() : java.math.BigDecimal.ZERO))
                .shippingFee(BigDecimal.valueOf(30000))
                .discountAmount(order.getPromotionValue() != null ? order.getPromotionValue() : java.math.BigDecimal.ZERO)
                .totalAmount(order.getSubTotal())
                .paymentMethod(order.getPaymentMethod().toString())
                .orderStatus(order.getOrderStatus().toString())
                .message("Thanh toán thành công!")
                .build();
    }
}
