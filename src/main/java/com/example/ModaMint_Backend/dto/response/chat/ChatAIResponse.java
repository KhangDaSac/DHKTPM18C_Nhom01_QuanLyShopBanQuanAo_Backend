package com.example.ModaMint_Backend.dto.response.chat;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatAIResponse {
    String message;
    ProductResponse product;
}
