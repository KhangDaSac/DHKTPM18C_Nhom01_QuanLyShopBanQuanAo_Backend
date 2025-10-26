package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.chat.ChatRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.chat.ChatAIResponse;
import com.example.ModaMint_Backend.service.ChatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatController {
    ChatService chatService;
    @PostMapping("/ai")
    ApiResponse<ChatAIResponse> chat(@RequestBody ChatRequest request) {
        return ApiResponse.<ChatAIResponse>builder()
                .result(chatService.chat(request))
                .message("Thành công")
                .build();
    }

    @PostMapping("/ai/image")
    ApiResponse<String> chatWithImage(@RequestParam("file") MultipartFile file, @RequestParam("message") String message) {
        return ApiResponse.<String>builder()
                .result(chatService.chatWithImage(file, message))
                .message("Thành công")
                .build();
    }
}
