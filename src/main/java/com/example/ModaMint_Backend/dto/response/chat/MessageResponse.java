package com.example.ModaMint_Backend.dto.response.chat;

import com.example.ModaMint_Backend.enums.SenderType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageResponse {
    Long id;
    String content;
    LocalDateTime timestamp;
    SenderType senderType;
}
