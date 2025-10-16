package com.example.ModaMint_Backend.entity;

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

    String status;

    @Column(name = "user_id")
    String userId;

    String note;

    @CreationTimestamp
    @Column(name = "create_at")
    LocalDateTime createAt;

    @Column(name = "order_id")
    Long orderId;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    Order order;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    User user;
}
