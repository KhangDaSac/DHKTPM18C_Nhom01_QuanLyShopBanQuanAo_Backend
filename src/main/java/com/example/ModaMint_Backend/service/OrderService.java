package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.order.OrderRequest;
import com.example.ModaMint_Backend.dto.response.cart.CartItemDto;
import com.example.ModaMint_Backend.dto.response.checkout.CheckoutResponse;
import com.example.ModaMint_Backend.dto.response.order.OrderResponse;
import com.example.ModaMint_Backend.entity.Order;
import com.example.ModaMint_Backend.enums.OrderStatus;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.mapper.OrderMapper;
import com.example.ModaMint_Backend.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderService {
    OrderRepository orderRepository;
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
        OrderStatus orderStatus = OrderStatus.valueOf(status); // Convert String to enum
        return orderRepository.findByOrderStatus(orderStatus)
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    public void updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        order.setOrderStatus(status); // Truyền trực tiếp enum
        orderRepository.save(order);
    }

    public long getTotalOrderCount() {
        return orderRepository.count();
    }

    public OrderResponse getOrderDetailById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return orderMapper.toOrderResponse(order);
    }
    
    /**
     * Gửi email xác nhận đơn hàng sau khi thanh toán thành công
     * Được gọi sau khi thanh toán VNPay thành công
     */
    public void sendOrderConfirmationEmailById(Long orderId) {
        try {
            log.info("=== START SENDING EMAIL FOR ORDER {} ===", orderId);
            
            // Lấy thông tin đơn hàng
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
            
            log.info("Order found: {}, status: {}", order.getOrderCode(), order.getOrderStatus());
            
            // Build CheckoutResponse từ Order để gửi email
            log.info("Building checkout response...");
            CheckoutResponse response = buildCheckoutResponseFromOrder(order);
            log.info("Checkout response built successfully");
            
            // Lấy email người nhận
            String recipientEmail = order.getCustomer().getUser() != null 
                ? order.getCustomer().getUser().getEmail() 
                : order.getCustomer().getEmail();
            
            log.info("Recipient email: {}", recipientEmail);
            
            // Gửi email
            emailService.sendOrderConfirmationEmail(response, recipientEmail);
            log.info("=== EMAIL SENT SUCCESSFULLY TO: {} ===", recipientEmail);
            
        } catch (Exception e) {
            log.error("=== FAILED TO SEND EMAIL FOR ORDER {} ===", orderId);
            log.error("Error: ", e);
            // Không throw exception - email không nên làm fail payment flow
        }
    }
    
    /**
     * Build CheckoutResponse từ Order entity để gửi email
     */
    private CheckoutResponse buildCheckoutResponseFromOrder(Order order) {
        // Sử dụng OrderMapper để convert sang OrderResponse
        OrderResponse orderResponse = orderMapper.toOrderResponse(order);
        
        // Build AddressResponse manually từ Order.shippingAddress
        com.example.ModaMint_Backend.dto.response.customer.AddressResponse addressResponse = null;
        if (order.getShippingAddress() != null) {
            addressResponse = com.example.ModaMint_Backend.dto.response.customer.AddressResponse.builder()
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
                .subtotal(order.getTotalAmount().subtract(java.math.BigDecimal.valueOf(30000)).add(order.getPromotionValue() != null ? order.getPromotionValue() : java.math.BigDecimal.ZERO))
                .shippingFee(java.math.BigDecimal.valueOf(30000))
                .discountAmount(order.getPromotionValue() != null ? order.getPromotionValue() : java.math.BigDecimal.ZERO)
                .totalAmount(order.getSubTotal())
                .paymentMethod(order.getPaymentMethod().toString())
                .orderStatus(order.getOrderStatus().toString())
                .message("Thanh toán thành công!")
                .build();
    }
}
