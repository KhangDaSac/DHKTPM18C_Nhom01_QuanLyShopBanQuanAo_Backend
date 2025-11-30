package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    long oderId;

    String phone;

    @Embedded
    Address shippingAddress;

    @Column(name = "total_promotion")
    double totalPromotion;

    @Column(name = "shipping_fee")
    double shippingFee;

    String carrier;

    @ElementCollection
    @CollectionTable(
            name = "order_status_histories",
            joinColumns = @JoinColumn(name = "order_id")
    )
    List<OrderStatusHistory> orderStatusHistories;

    @Column(name = "tracking_number")
    String trackingNumber;

    @Column(name = "expected_delivery_date")
    LocalDate expectedDeliveryDate;

    @Embedded
    Payment payment;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    Promotion promotion;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @OneToMany(mappedBy = "order")
    @ToString.Exclude
    List<OrderItem> orderItems;

    public double getTotalOrder(){
        double itemsTotal = orderItems.stream()
                .mapToDouble(OrderItem::getLineTotal)
                .sum();
        return itemsTotal + shippingFee - totalPromotion;
    }
}