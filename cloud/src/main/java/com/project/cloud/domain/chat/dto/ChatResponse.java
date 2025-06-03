package com.project.cloud.domain.chat.dto;

import com.project.cloud.domain.routine.dto.request.RoutineRequest;

import java.util.List;

public class ChatResponse {

    public record Chat (
            String type,
            Qa qa,
            Routine routine
    ) {
        public static Chat from(String type, Qa qa, Routine routine) {
            if (type.equals("qa")) {
                return new Chat(type, qa, null);
            }

            return new Chat(type, null, routine);
        }
    }

    public record Qa (
            String type,
            String question,
            String answer
    ) {}

    public record Routine (
            String type,
            List<String> preferredParts,
            String level,
            String goal,
            Integer frequencyPerWeek,
            RoutineRequest routine
    ) {}
}
