package com.project.cloud.domain.chat.dto;

public class ChatRequest {

    public record Chat(
            String email,
            String message
    ) {}
}
