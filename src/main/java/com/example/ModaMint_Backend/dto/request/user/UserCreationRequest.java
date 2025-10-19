package com.example.ModaMint_Backend.dto.request.user;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;
    String email;
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    String phone;
    String firstName;
    String lastName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dob;
    String image;

    Set<String> roles;
}
