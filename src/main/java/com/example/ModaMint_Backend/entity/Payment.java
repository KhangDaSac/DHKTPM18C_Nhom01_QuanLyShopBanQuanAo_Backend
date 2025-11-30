package com.example.ModaMint_Backend.entity;


import com.example.ModaMint_Backend.enums.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    PaymentMethod paymentMethod;

    @Column(name = "payment_amount")
    double paymentAmount;
    
    @Column(name = "payment_status")
    String paymentStatus;

    @Column(name = "transaction_code")
    String transactionCode;

    String payload;

    LocalDateTime timestamp;
}
