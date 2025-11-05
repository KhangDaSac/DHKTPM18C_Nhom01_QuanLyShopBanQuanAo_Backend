package com.example.ModaMint_Backend.entity;

import com.example.ModaMint_Backend.entity.Address;
import com.example.ModaMint_Backend.entity.Cart;
import com.example.ModaMint_Backend.entity.Order;
import com.example.ModaMint_Backend.entity.Review;
import com.example.ModaMint_Backend.entity.User;
import com.example.ModaMint_Backend.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "customers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {
    @Id
    String customerId;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "customer")
    Set<Address> addresses;

    @OneToOne(mappedBy = "customer")
    Cart cart;

    @OneToMany(mappedBy = "customer")
    Set<Order> orders;


    @OneToMany(mappedBy = "customer")
    Set<Review> reviews;
}