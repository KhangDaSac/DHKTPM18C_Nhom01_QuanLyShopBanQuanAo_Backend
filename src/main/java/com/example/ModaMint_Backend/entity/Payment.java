package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "order_id")
    Long orderId;

    String method; // Mã phương thức thanh toán (COD, ZALO, VNPAY, ...)
    BigDecimal amount; // Số tiền thanh toán
    String status; // Trạng thái thanh toán (Chưa thanh toán, Đã thanh toán, Lỗi thanh toán)

    @Column(name = "transaction_id")
    String transactionId; // Mã giao dịch thanh toán

    String payload;     // Dữ liệu thanh toán 

    @CreationTimestamp
    @Column(name = "create_at")
    LocalDateTime createAt;

    @OneToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    Order order;
}
