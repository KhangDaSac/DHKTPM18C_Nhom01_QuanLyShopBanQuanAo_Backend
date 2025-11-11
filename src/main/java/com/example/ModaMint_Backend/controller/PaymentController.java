package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.payment.PaymentRequest;
import com.example.ModaMint_Backend.dto.response.payment.PaymentResponse;
import com.example.ModaMint_Backend.enums.OrderStatus;
import com.example.ModaMint_Backend.service.VnPayService;
import com.example.ModaMint_Backend.service.OrderService; // Giả sử bạn có OrderService
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final VnPayService vnPayService;
    private final OrderService orderService;

    // Xử lý tạo URL thanh toán
    @PostMapping("/create-payment")
    public ResponseEntity<?> createPayment(HttpServletRequest request,
                                           @RequestBody PaymentRequest paymentRequest) { // Sử dụng RequestBody để nhận từ frontend
        long amount = paymentRequest.getAmount();
        String orderInfo = paymentRequest.getOrderInfo();

        String vnpayUrl = vnPayService.createPaymentUrl(request, amount, orderInfo);
        return ResponseEntity.ok(new PaymentResponse(vnpayUrl)); // Trả về { paymentUrl: vnpayUrl }
    }

    // Xử lý kết quả trả về từ VNPAY
//    @GetMapping("/vnpay-return")
//    public  ResponseEntity<?> handleVnpayReturn(HttpServletRequest request) {
//        try {
//            int status = vnPayService.orderReturn(request);
//            String orderInfo = request.getParameter("vnp_OrderInfo");
//            String orderId = extractOrderIdFromOrderInfo(orderInfo);
//
//            // Update order status
//            if (status == 1) {
//                orderService.updateOrderStatus(Long.valueOf(orderId), OrderStatus.PREPARING);
//            } else {
//                orderService.updateOrderStatus(Long.valueOf(orderId), OrderStatus.CANCELLED);
//            }
//
//            // Trả về JSON response cho frontend
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", status == 1);
//            response.put("orderId", orderId);
//            response.put("message", status == 1 ? "Thanh toán thành công" : "Thanh toán thất bại");
//            response.put("transactionId", request.getParameter("vnp_TransactionNo"));
//            response.put("amount", request.getParameter("vnp_Amount"));
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Lỗi xử lý kết quả thanh toán: " + e.getMessage());
//        }
//    }
    @GetMapping("/vnpay-return")
    public void handleVnpayReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int status = vnPayService.orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String orderId = extractOrderIdFromOrderInfo(orderInfo);

        if (status == 1) {
            orderService.updateOrderStatus(Long.valueOf(orderId), OrderStatus.PREPARING);
            // ✅ Redirect trực tiếp đến trang success trên frontend
            response.sendRedirect("http://localhost:5173/order-success/" + orderId);
        } else {
            orderService.updateOrderStatus(Long.valueOf(orderId), OrderStatus.CANCELLED);
            // ❌ Redirect đến trang thất bại
            response.sendRedirect("http://localhost:5173/order-failed/" + orderId);
        }
    }



    // Hàm extract orderId từ orderInfo (ví dụ)
    private String extractOrderIdFromOrderInfo(String orderInfo) {
        // Logic đơn giản: split và lấy phần cuối
        String[] parts = orderInfo.split(" ");
        return parts[parts.length - 1];
    }

    // DTO cho request/response



}
