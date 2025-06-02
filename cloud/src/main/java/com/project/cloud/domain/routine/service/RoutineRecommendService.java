package com.project.cloud.domain.routine.service;

import com.project.cloud.domain.routine.dto.request.RoutineRequest;
import com.project.cloud.domain.routine.dto.response.RoutineResponse;
import com.project.cloud.domain.user.entity.User;
import com.project.cloud.domain.user.repository.UserRepository;
import com.project.cloud.global.exception.CustomException;
import com.project.cloud.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class RoutineRecommendService {
     RoutineService routineService;
     private final RestTemplate restTemplate;
     private final UserRepository userRepository;

     @Value("${ai.recommend-url}")
     private String recommendUrl;

     private User findUserByEmail(String email) {
          return userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXIST));
     }

     @Transactional
     public RoutineResponse recommendAndSaveRoutine(String email){
          User user = findUserByEmail(email);
          Map<String, Object> request = new HashMap<>();
          request.put("goal", user.getGoal().name().toLowerCase());
          request.put("gender", user.getGender().name());
          request.put("weight", user.getWeight());
          request.put("level", user.getWorkoutLevel().name().toLowerCase());
          request.put("preferred_parts", getPreferredParts(user));
          request.put("top_k",5);

          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.APPLICATION_JSON);
          HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

          ResponseEntity<RoutineRequest> response = restTemplate.postForEntity(recommendUrl, entity, RoutineRequest.class);
          RoutineRequest routineRequest = response.getBody();
          if (routineRequest == null) {
               throw new CustomException(ErrorCode.ROUTINE_RECOMMEND_FAILED);
          }

          return routineService.createRoutine(routineRequest, email);
     }

     private List<String> getPreferredParts(User user) {
          return user.getBodyPartStats().stream()
                  .map(stat -> stat.getBodyPart().getName())
                  .toList();
     }



}