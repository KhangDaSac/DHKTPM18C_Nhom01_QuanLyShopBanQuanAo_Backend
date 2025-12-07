//package com.example.ModaMint_Backend.entity;
//
//import com.example.ModaMint_Backend.entity.Order;
//import com.example.ModaMint_Backend.enums.PaymentMethod;
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//import org.hibernate.annotations.CreationTimestamp;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "payments")
//@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class Payment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long id;
//
//    @Column(name = "order_id")
//    Long orderId;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "payment_method")
//    PaymentMethod paymentMethod; // Phương thức thanh toán (COD, BANK_TRANSFER, E_WALLET)
//
//    BigDecimal amount; // Số tiền thanh toán
//
//    @Column(name = "payment_status")
//    String paymentStatus; // Trạng thái thanh toán (PENDING, PAID, FAILED, REFUNDED)
//
//    @Column(name = "transaction_id")
//    String transactionId; // Mã giao dịch thanh toán
//
//    String payload;     // Dữ liệu thanh toán
//
//    @CreationTimestamp
//    @Column(name = "create_at")
//    LocalDateTime createAt;
//
//    @OneToOne
//    @JoinColumn(name = "order_id", insertable = false, updatable = false)
//    Order order;
//}
