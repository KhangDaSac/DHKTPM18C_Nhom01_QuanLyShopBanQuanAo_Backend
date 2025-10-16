package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Entity
@Table(name = "customers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {
    @Id
    String userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    User user;

    String address;
    LocalDate birthdate;
    Integer loyaltyPoints;
}