package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.chat.ChatRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.service.ChatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatController {
    ChatService chatService;
    @PostMapping
    ApiResponse<String> chat(@RequestBody ChatRequest request) {
        return ApiResponse.<String>builder()
                .result(chatService.chat(request))
                .message("Thành công")
                .build();
    }
}
