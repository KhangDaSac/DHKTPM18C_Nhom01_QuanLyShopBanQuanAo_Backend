package com.example.ModaMint_Backend.dto.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String email;
    String phone;
    String firstName;
    String lastName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dob;
    String image;
}
