package com.project.cloud.domain.chat.dto;

public class ChatResponse {

    public record Chat (
            String type,
            String qaAnswer,
            Long routineId
    ) {
        public static Chat from(String type, String qaAnswer, Long routineId) {
            return new Chat(
                    type,
                    qaAnswer,
                    routineId
            );
        }
    }
}
