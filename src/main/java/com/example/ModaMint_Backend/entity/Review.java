package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    long reviewId;

    int rating;

    String comment;

    @ElementCollection
    @CollectionTable(
            name = "review_images",
            joinColumns = @JoinColumn(name = "review_id")
    )
    List<String> images;

    LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "order_id", referencedColumnName = "order_id"),
            @JoinColumn(name = "product_variant_id", referencedColumnName = "product_variant_id")
    })
    OrderItem orderItem;
}
