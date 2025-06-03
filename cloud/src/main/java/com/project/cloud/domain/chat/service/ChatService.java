package com.project.cloud.domain.chat.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.cloud.domain.chat.dto.ChatRequest;
import com.project.cloud.domain.chat.dto.ChatResponse;
import com.project.cloud.domain.routine.dto.request.RoutineRequest;
import com.project.cloud.domain.routine.dto.response.RoutineResponse;
import com.project.cloud.domain.routine.service.RoutineService;
import com.project.cloud.domain.user.entity.User;
import com.project.cloud.domain.user.repository.UserRepository;
import com.project.cloud.global.exception.CustomException;
import com.project.cloud.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final RoutineService routineService;

    @Value("${ai.chatbot-url}") private String chatbotUrl;

    @Transactional
    public ChatResponse.Chat askChatBot(ChatRequest.Chat request) {

        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXIST));

        // AI 챗봇 서버로 보낼 JSON 바디 생성
        Map<String, Object> body = new HashMap<>();
        body.put("message", request.message());

        // userData 맵 생성
        Map<String, Object> userData = new HashMap<>();
        userData.put("goal", user.getGoal().name().toLowerCase());
        userData.put("preferred_parts",
                user.getBodyPartStats().stream()
                        .map(stat -> stat.getBodyPart().getName())
                        .toList()
        );
        userData.put("level", user.getWorkoutLevel().name().toLowerCase());
        userData.put("gender", user.getGender().name());
        userData.put("weight", user.getWeight());
        userData.put("top_k", 3);

        body.put("userData", userData);

        // RestTemplate으로 챗봇 서버에 POST 요청
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> respEntity;
        try {
            respEntity = restTemplate.exchange(
                    chatbotUrl,
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );
        } catch (Exception e) {
            // 네트워크 오류 등, 챗봇 서버 요청 실패 시
            return ChatResponse.Chat.from("error", "챗봇 서버 요청 실패: " + e.getMessage(), null);
        }

        String responseJson = respEntity.getBody();

        // ChatResponse.Chat 객체 생성/반환
        try {
            JsonNode root = objectMapper.readTree(responseJson);
            String type = root.path("type").asText();

            if ("qa".equals(type)) {
                String question = root.path("question").asText(null);
                String answer   = root.path("answer").asText(null);

                return ChatResponse.Chat.from(type, answer, null);
            }
            else if ("routine".equals(type)) {
                List<String> preferredParts = new ArrayList<>();
                root.path("preferred_parts").forEach(node -> preferredParts.add(node.asText()));

                String level              = root.path("level").asText(null);
                String goal               = root.path("goal").asText(null);
                Integer frequencyPerWeek  = root.path("frequency_per_week").asInt();

                // "routine" 오브젝트 내부 파싱
                JsonNode routineNode = root.path("routine");
                String routineName   = routineNode.path("name").asText(null);

                // routineItems 배열 파싱
                List<RoutineRequest.RoutineItemDto> items = new ArrayList<>();
                routineNode.path("routineItems").forEach(itemNode -> {
                    Long    exerciseId = itemNode.path("exerciseId").asLong();
                    Integer sets       = itemNode.path("sets").asInt();
                    Integer reps       = itemNode.path("reps").asInt();
                    Integer weight     = itemNode.path("weight").asInt();
                    Integer order      = itemNode.path("order").asInt();

                    items.add(new RoutineRequest.RoutineItemDto(exerciseId, sets, reps, weight, order));
                });

                // RoutineRequest 생성
                RoutineRequest rr = new RoutineRequest(routineName, items);

                RoutineResponse saved = routineService.createRoutine(rr, user.getEmail());
                return ChatResponse.Chat.from(type, null, saved.getRoutineId());
            }
            else {
                return ChatResponse.Chat.from("error", "알 수 없는 응답 형식(type=" + type + ")", null);
            }
        }
        catch (Exception e) {
            return ChatResponse.Chat.from("error", "JSON 파싱 오류: " + e.getMessage(), null);
        }
    }
}
