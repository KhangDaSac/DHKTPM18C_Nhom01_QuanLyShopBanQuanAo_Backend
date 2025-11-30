package com.example.ModaMint_Backend.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {
    @Id
    @Column(name = "customer_id")
    long customerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User user;

    @ElementCollection
    @CollectionTable(
            name = "customer_addresses",
            joinColumns = @JoinColumn(name = "customer_id")
    )
    List<Address> addresses;

    @OneToOne(mappedBy = "customer")
    Cart cart;

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    List<Order> orders;

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    List<Review> reviews;

    @ManyToMany
    @JoinTable(
            name = "customer_favorite_products",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @ToString.Exclude
    List<Product> favoriteProducts;
}