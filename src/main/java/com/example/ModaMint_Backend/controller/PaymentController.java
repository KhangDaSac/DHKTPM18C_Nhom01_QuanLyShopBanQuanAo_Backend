package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.payment.PaymentRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.payment.PaymentResponse;
import com.example.ModaMint_Backend.entity.Order;
import com.example.ModaMint_Backend.enums.OrderStatus;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.repository.OrderRepository;
import com.example.ModaMint_Backend.service.VnPayService;
import com.example.ModaMint_Backend.service.OrderService;
import com.example.ModaMint_Backend.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final VnPayService vnPayService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    // Xử lý tạo URL thanh toán
    @PostMapping("/create-payment")
    public ResponseEntity<?> createPayment(HttpServletRequest request,
                                           @RequestBody PaymentRequest paymentRequest) {
        long amount = paymentRequest.getAmount();
        String orderInfo = paymentRequest.getOrderInfo();

        String vnpayUrl = vnPayService.createPaymentUrl(request, amount, orderInfo);
        return ResponseEntity.ok(new PaymentResponse(vnpayUrl));
    }

    /**
     * Tạo lại payment URL cho đơn hàng PENDING (dùng khi khách hàng muốn thanh toán
     * lại)
     */
    @PostMapping("/retry-payment/{orderId}")
    public ResponseEntity<?> retryPayment(
            @PathVariable Long orderId,
            HttpServletRequest request) {

        // 1. Kiểm tra order tồn tại
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        // 2. Kiểm tra order có status PENDING không
        if (order.getOrderStatus() != OrderStatus.PENDING) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .code(4000)
                            .message("Don hang khong o trang thai cho thanh toan")
                            .build());
        }

        // 3. Kiểm tra order chưa quá 15 phút
        LocalDateTime createAt = order.getCreateAt();
        LocalDateTime now = LocalDateTime.now();
        long minutesElapsed = ChronoUnit.MINUTES.between(createAt, now);

        if (minutesElapsed >= 15) {
            // Tự động hủy đơn hàng
            orderService.updateOrderStatus(orderId, OrderStatus.CANCELLED);

            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .code(4001)
                            .message("Don hang da qua thoi gian thanh toan (15 phut). Don hang da duoc tu dong huy.")
                            .build());
        }

        // 4. Tạo payment URL mới
        String vnpayUrl = vnPayService.createPaymentUrlForOrder(request, order);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .code(2000)
                        .message("Tao link thanh toan thanh cong")
                        .result(new PaymentResponse(vnpayUrl))
                        .build());
    }

    // Xử lý kết quả trả về từ VNPAY
    @GetMapping("/vnpay-return")
    public void handleVnpayReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("\n\n========================================");
        System.out.println("VNPAY CALLBACK RECEIVED");
        System.out.println("========================================");
        
        int status = vnPayService.orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String orderId = extractOrderIdFromOrderInfo(orderInfo);
        
        System.out.println("Payment Status: " + (status == 1 ? "SUCCESS" : "FAILED"));
        System.out.println("Order ID: " + orderId);

        if (status == 1) {
            try {
                System.out.println("\n>>> STEP 1: Updating order status to PREPARING...");
                orderService.updateOrderStatus(Long.valueOf(orderId), OrderStatus.PREPARING);
                System.out.println(">>> Order status updated successfully");
                
                System.out.println("\n>>> STEP 2: Clearing cart for customer...");
                // Lấy thông tin order để lấy customerId
                Order order = orderRepository.findById(Long.valueOf(orderId))
                        .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
                String customerId = order.getCustomerId();
                
                // Xóa giỏ hàng của khách hàng (cho cả registered user, guest tự xóa ở frontend)
                if (customerId != null && !customerId.startsWith("GUEST_")) {
                    try {
                        cartService.clearCart(customerId);
                        System.out.println(">>> Cart cleared for customer: " + customerId);
                    } catch (Exception cartException) {
                        // Cart có thể đã được xóa rồi trong checkout process - không cần throw error
                        System.out.println(">>> Cart already cleared or not found for customer: " + customerId);
                    }
                } else {
                    System.out.println(">>> Guest customer - cart will be cleared on frontend");
                }
                
                System.out.println("\n>>> STEP 3: Sending confirmation email...");
                orderService.sendOrderConfirmationEmailById(Long.valueOf(orderId));
                System.out.println(">>> Email sending process completed");
                
                System.out.println("\n>>> STEP 4: Redirecting to success page...");
                response.sendRedirect("http://localhost:5173/order-success/" + orderId);
                System.out.println("========================================\n\n");
            } catch (Exception e) {
                System.err.println("!!! ERROR in VNPay success handling: " + e.getMessage());
                e.printStackTrace();
                response.sendRedirect("http://localhost:5173/order-failed/" + orderId);
            }
        } else {
            System.out.println("\n>>> Payment failed - Order remains PENDING");
            System.out.println(">>> Customer can retry payment within 15 minutes");
            System.out.println("========================================\n\n");
            response.sendRedirect("http://localhost:5173/order-failed/" + orderId);
        }
    }

    // Hàm extract orderId từ orderInfo
    // Format: "Thanh toan don hang <orderId>"
    // Ví dụ: "Thanh toan don hang 1" hoặc "Thanh toan don hang 123"
    private String extractOrderIdFromOrderInfo(String orderInfo) {
        if (orderInfo == null || orderInfo.isEmpty()) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }

        // Split bằng space và lấy phần cuối cùng (là orderId)
        String[] parts = orderInfo.split(" ");
        String orderId = parts[parts.length - 1];

        // Kiểm tra orderId có phải là số hợp lệ không
        try {
            Long.parseLong(orderId);
            return orderId;
        } catch (NumberFormatException e) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }
    }
}
