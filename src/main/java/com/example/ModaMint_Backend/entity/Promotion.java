package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "promotions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String code; // Mã khuyến mãi ví dụ: "WELCOME10"

    String value; // Giá trị khuyến mãi ví dụ: 10% hoặc 100.000 VNĐ

    @Column(name = "min_order_value")
    BigDecimal minOrderValue; // Giá trị tối thiểu để áp dụng khuyến mãi ví dụ: 100000

    @Column(name = "start_at")
    LocalDateTime startAt; 

    @Column(name = "end_at")
    LocalDateTime endAt; 

    @Column(name = "quantity")
    Integer quantity; // Số lượng khuyến mãi
 
    @Column(name = "is_active")
    Boolean isActive; // Trạng thái khuyến mãi (true: khuyến mãi đang hoạt động, false: khuyến mãi đã hết hạn)

    @CreationTimestamp
    @Column(name = "create_at")
    LocalDateTime createAt; 

    @OneToMany(mappedBy = "promotion")
    Set<Order> orders;  

    @OneToMany(mappedBy = "promotion")
    Set<TypePromotion> typePromotions; // Loại khuyến mãi (Phần trăm, tiền)
}
