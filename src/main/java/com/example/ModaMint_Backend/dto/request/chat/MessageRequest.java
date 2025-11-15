package com.example.ModaMint_Backend.dto.request.chat;

import com.example.ModaMint_Backend.enums.SenderType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageRequest {
    Long conversationId;
    String content;
    SenderType senderType;
}
