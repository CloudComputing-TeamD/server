package com.project.cloud.domain.chat.controller;

import com.project.cloud.domain.chat.dto.ChatRequest;
import com.project.cloud.domain.chat.dto.ChatResponse;
import com.project.cloud.domain.chat.service.ChatService;
import com.project.cloud.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public SuccessResponse<ChatResponse.Chat> chat(@RequestBody ChatRequest.Chat request) {
        return SuccessResponse.ok(chatService.askChatBot(request));
    }
}
