package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.response.checkout.CheckoutResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.text.NumberFormat;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendOrderConfirmationEmail(CheckoutResponse orderData, String recipientEmail) {
        if (recipientEmail == null || recipientEmail.isBlank()) {
            log.warn("Recipient email is null/empty - skipping sending order confirmation for order {}", orderData != null ? orderData.getOrderCode() : "<unknown>");
            return;
        }

        try {
            log.info("Sending order confirmation email to: {}", recipientEmail);
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom("noreply@modamint.com");
            helper.setTo(recipientEmail);
            helper.setSubject("Xác nhận đơn hàng #" + orderData.getOrderCode());

            Context context = new Context();
            context.setVariable("orderCode", orderData.getOrderCode());
            context.setVariable("customerName", orderData.getCustomerName());
            context.setVariable("customerEmail", orderData.getCustomerEmail());
            context.setVariable("customerPhone", orderData.getCustomerPhone());
            context.setVariable("shippingAddress", orderData.getShippingAddress().getFullAddress());
            context.setVariable("orderItems", orderData.getOrderItems());
            context.setVariable("subtotal", formatCurrency(orderData.getSubtotal()));
            context.setVariable("shippingFee", formatCurrency(orderData.getShippingFee()));
            context.setVariable("discountAmount", formatCurrency(orderData.getDiscountAmount()));
            context.setVariable("totalAmount", formatCurrency(orderData.getTotalAmount()));
            context.setVariable("paymentMethod", getPaymentMethodText(orderData.getPaymentMethod()));
            
            String htmlContent = templateEngine.process("order-confirmation", context);
            helper.setText(htmlContent, true);
            
            mailSender.send(message);
            log.info("Order confirmation email sent successfully to: {}", recipientEmail);
            
        } catch (MessagingException e) {
            log.error("Failed to send order confirmation email to: {}", recipientEmail, e);
            // Don't throw exception - email failure shouldn't fail the order
        } catch (Exception e) {
            log.error("Unexpected error while sending email to: {}", recipientEmail, e);
        }
    }
    
    private String formatCurrency(java.math.BigDecimal amount) {
        if (amount == null) return "0đ";
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(amount);
    }
    
    private String getPaymentMethodText(String paymentMethod) {
        return switch (paymentMethod) {
            case "CASH_ON_DELIVERY" -> "Thanh toán khi nhận hàng";
            case "BANK_TRANSFER" -> "Chuyển khoản ngân hàng";
            case "E_WALLET" -> "Ví điện tử";
            default -> paymentMethod;
        };
    }
}
