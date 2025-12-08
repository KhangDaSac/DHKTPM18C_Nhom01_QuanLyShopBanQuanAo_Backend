package com.example.ModaMint_Backend.dto.request.payment;

public class PaymentRequest {
    private long amount;
    private String orderInfo;

    // Getters/setters
    public long getAmount() { return amount; }
    public void setAmount(long amount) { this.amount = amount; }
    public String getOrderInfo() { return orderInfo; }
    public void setOrderInfo(String orderInfo) { this.orderInfo = orderInfo; }
}
