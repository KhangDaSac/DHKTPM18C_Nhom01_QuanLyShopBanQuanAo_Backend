package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.chat.ChatAiRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.chat.ChatAiResponse;
import com.example.ModaMint_Backend.service.ChatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/chat"))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ChatController {
    ChatService chatService;

    @PostMapping("/chat-ai")
    public ApiResponse<ChatAiResponse> chatAi(@RequestBody ChatAiRequest request) {
        return ApiResponse.<ChatAiResponse>builder()
                .result(chatService.chatAi(request))
                .message("Chat với bot thành công")
                .build();
    }

    @GetMapping("/chat-ai/history")
    public ApiResponse<List<ChatAiResponse>> getChatHistory() {
        return ApiResponse.<List<ChatAiResponse>>builder()
                .message("Lấy lịch sử chat thành công")
                .result(chatService.getFullHistory())
                .build();
    }
}
