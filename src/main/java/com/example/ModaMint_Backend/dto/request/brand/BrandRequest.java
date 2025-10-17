package com.example.ModaMint_Backend.dto.request.brand;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandRequest {

    @NotBlank(message = "Tên thương hiệu không được để trống")
    String name;

    String description;

    String logoUrl;
}
