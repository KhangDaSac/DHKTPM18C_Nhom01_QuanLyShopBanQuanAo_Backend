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
        int status = vnPayService.orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String orderId = extractOrderIdFromOrderInfo(orderInfo);

        if (status == 1) {
            orderService.updateOrderStatus(Long.valueOf(orderId), OrderStatus.PREPARING);
            response.sendRedirect("http://localhost:5173/order-success/" + orderId);
        } else {
            orderService.updateOrderStatus(Long.valueOf(orderId), OrderStatus.CANCELLED);
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
