package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "cart")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "customer_id")
    String customerId;

    @UpdateTimestamp
    @Column(name = "update_at")
    LocalDateTime updateAt;

    @CreationTimestamp
    @Column(name = "create_at")
    LocalDateTime createAt;

    @OneToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    Customer customer;

    @OneToMany(mappedBy = "cart")
    Set<CartItem> cartItems;
}
