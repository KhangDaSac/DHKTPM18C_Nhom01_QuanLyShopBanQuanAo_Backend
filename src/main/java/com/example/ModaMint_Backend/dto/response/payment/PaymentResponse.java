package com.example.ModaMint_Backend.dto.response.payment;

public class PaymentResponse {
    private String paymentUrl;

    public PaymentResponse(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getPaymentUrl() { return paymentUrl; }
    public void setPaymentUrl(String paymentUrl) { this.paymentUrl = paymentUrl; }
}