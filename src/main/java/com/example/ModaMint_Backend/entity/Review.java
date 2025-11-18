package com.example.ModaMint_Backend.entity;

<<<<<<< HEAD

import com.example.ModaMint_Backend.entity.Customer;
import com.example.ModaMint_Backend.entity.OrderItem;
import com.example.ModaMint_Backend.entity.Product;

=======
import com.example.ModaMint_Backend.entity.Customer;
import com.example.ModaMint_Backend.entity.OrderItem;
import com.example.ModaMint_Backend.entity.Product;
>>>>>>> origin/main
import com.example.ModaMint_Backend.converter.StringSetConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "reviews")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "product_id")
    Long productId;

    @Column(name = "customer_id")
    String customerId;

    @Column(name = "order_item_id")
    Long orderItemId;

    Integer rating;

    String comment;

    @Convert(converter = StringSetConverter.class)
    @Column(name = "images", columnDefinition = "TEXT")
    Set<String> images;

    @CreationTimestamp
    @Column(name = "create_at")
    LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    Customer customer;

    @ManyToOne
    @JoinColumn(name = "order_item_id", insertable = false, updatable = false)
    OrderItem orderItem;
}
