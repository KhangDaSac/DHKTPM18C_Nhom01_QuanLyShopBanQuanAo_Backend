package com.example.ModaMint_Backend.entity;

import com.example.ModaMint_Backend.entity.*;
import com.example.ModaMint_Backend.enums.OrderStatus;
import com.example.ModaMint_Backend.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "order_code")
    String orderCode;

    @Column(name = "customer_id")
    String customerId;

    @Column(name = "total_amount")
    BigDecimal totalAmount; // Tổng tiền hàng (Tổng tiền hàng + phí vận chuyển)

    @Column(name = "sub_total")
    BigDecimal subTotal; // Tổng tiền hàng - khuyến mãi (Tổng tiền cuối cùng)

    @Column(name = "percentage_promotion_id")
    Long percentPromotionId;

    @Column(name = "amount_promotion_id")
    Long amountPromotionId; 

    @Column(name = "promotion_value") 
    BigDecimal promotionValue; // Phải lưu vì theo thời gian promotion có thể thay đổi

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    OrderStatus orderStatus;  // Chỉ lưu trạng thái đơn hàng (Chờ xác nhận, Đang giao, Đã giao, Đã hủy)

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    PaymentMethod paymentMethod; // Phương thức thanh toán (COD, BANK_TRANSFER, E_WALLET)

    @Column(name = "shipping_address_id")
    Long shippingAddressId;

    String phone; // Số điện thoại người mua

    @UpdateTimestamp
    @Column(name = "update_at")
    LocalDateTime updateAt;

    @CreationTimestamp
    @Column(name = "create_at")
    LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    Customer customer;

    @ManyToOne
    @JoinColumn(name = "percentage_promotion_id", insertable = false, updatable = false)
    PercentPromotion percentPromotion;

    @ManyToOne
    @JoinColumn(name = "amount_promotion_id", insertable = false, updatable = false)
    AmountPromotion amountPromotion;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id", insertable = false, updatable = false)
    Address shippingAddress;

    @OneToMany(mappedBy = "order")
    Set<OrderItem> orderItems;

    @OneToMany(mappedBy = "order")
    Set<OrderStatusHistory> orderStatusHistories;

    @OneToMany(mappedBy = "order")
    Set<Shipment> shipments;

//    @OneToOne(mappedBy = "order")
//    Payment payment;

    public BigDecimal totalProductPrice() {
        return orderItems.stream()
                .map(OrderItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal shippingFee() {
        return BigDecimal.valueOf(30000);
    }

    public BigDecimal totalPromotion() {
        return promotionValue != null ? promotionValue : BigDecimal.ZERO;
    }

    public BigDecimal totalAmount() {
        return totalProductPrice().add(shippingFee()).subtract(totalPromotion());
    }

}