package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.payment.PaymentRequest;
import com.example.ModaMint_Backend.dto.response.payment.PaymentResponse;
import com.example.ModaMint_Backend.enums.OrderStatus;
import com.example.ModaMint_Backend.service.VnPayService;
import com.example.ModaMint_Backend.service.OrderService; // Giả sử bạn có OrderService
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final VnPayService vnPayService;
    private final OrderService orderService; // Inject OrderService

    // Hiển thị trang đặt hàng
    @GetMapping
    public String showOrderPage(){
        return "order"; // Trả về file order.html
    }

    // Xử lý tạo URL thanh toán (sửa để trả về JSON cho frontend axios)
    @PostMapping("/create-payment")
    public ResponseEntity<?> createPayment(HttpServletRequest request,
                                           @RequestBody PaymentRequest paymentRequest) { // Sử dụng RequestBody để nhận từ frontend
        long amount = paymentRequest.getAmount();
        String orderInfo = paymentRequest.getOrderInfo();
        // Nhân amount với 100 vì VNPAY tính theo đơn vị xu
        String vnpayUrl = vnPayService.createPaymentUrl(request, amount * 100, orderInfo);
        return ResponseEntity.ok(new PaymentResponse(vnpayUrl)); // Trả về { paymentUrl: vnpayUrl }
    }

    // Xử lý kết quả trả về từ VNPAY
    @GetMapping("/vnpay-return")
    public String handleVnpayReturn(HttpServletRequest request, Model model) {
        int status = vnPayService.orderReturn(request);
        String statusText = status == 1 ? "Thành công" : "Thất bại";

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String orderId = extractOrderIdFromOrderInfo(orderInfo);

        // Update order status với enum
        if (status == 1) {
            orderService.updateOrderStatus(Long.valueOf(orderId), OrderStatus.PREPARING);
        } else {
            orderService.updateOrderStatus(Long.valueOf(orderId), OrderStatus.CANCELLED);
        }

        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderId);
        model.addAttribute("totalPrice", Long.parseLong(totalPrice) / 100);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);
        model.addAttribute("status", statusText);

        return "payment-result";
    }

    // Hàm extract orderId từ orderInfo (ví dụ)
    private String extractOrderIdFromOrderInfo(String orderInfo) {
        // Logic đơn giản: split và lấy phần cuối
        String[] parts = orderInfo.split(" ");
        return parts[parts.length - 1];
    }

    // DTO cho request/response



}
