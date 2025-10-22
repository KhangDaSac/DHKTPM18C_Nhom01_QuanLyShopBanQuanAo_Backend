package com.example.ModaMint_Backend.entity;

import com.example.ModaMint_Backend.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_status_history")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "order_id")
    Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    OrderStatus orderStatus;

    String message;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    Order order;
}
