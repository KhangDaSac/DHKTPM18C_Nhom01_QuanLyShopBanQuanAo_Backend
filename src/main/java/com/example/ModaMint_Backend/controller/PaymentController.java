package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final VnPayService vnPayService;

    // Hiển thị trang đặt hàng
    @GetMapping
    public String showOrderPage(){
        return "order"; // Trả về file order.html
    }

    // Xử lý tạo URL thanh toán
    @PostMapping("/create-payment")
    public String createPayment(HttpServletRequest request,
                                @RequestParam("amount") long amount,
                                @RequestParam("orderInfo") String orderInfo) {
        // Nhân amount với 100 vì VNPAY tính theo đơn vị xu
        String vnpayUrl = vnPayService.createPaymentUrl(request, amount * 100, orderInfo);
        return "redirect:" + vnpayUrl;
    }

    // Xử lý kết quả trả về từ VNPAY
    @GetMapping("/vnpay-return")
    public String handleVnpayReturn(HttpServletRequest request, Model model) {
        int status = vnPayService.orderReturn(request);
        String statusText = status == 1 ? "Thành công" : "Thất bại";

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", Long.parseLong(totalPrice) / 100); // Chia lại cho 100
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);
        model.addAttribute("status", statusText);

        return "payment-result"; // Trả về file payment-result.html
    }
}
