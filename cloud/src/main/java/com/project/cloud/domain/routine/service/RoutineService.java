package com.project.cloud.domain.routine.service;

import com.project.cloud.domain.exercise.entity.Exercise;
import com.project.cloud.domain.exercise.repository.ExerciseRepository;
import com.project.cloud.domain.routine.dto.request.RoutineRequest;
import com.project.cloud.domain.routine.dto.response.RoutineResponse;
import com.project.cloud.domain.routine.entity.Routine;
import com.project.cloud.domain.routine.repository.RoutineRepository;
import com.project.cloud.domain.routineItem.entity.RoutineItem;
import com.project.cloud.domain.user.entity.User;
import com.project.cloud.domain.user.repository.UserRepository;
import com.project.cloud.global.exception.CustomException;
import com.project.cloud.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoutineService {
    private final RoutineRepository routineRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXIST));
    }

    @Transactional
    public RoutineResponse createRoutine(RoutineRequest request, String email){
        User user = findUserByEmail(email);

        boolean exists = routineRepository.existsByUserAndName(user, request.getName());
        if (exists){
            throw new CustomException(ErrorCode.ROUTINE_DUPLICATE_NAME);
        }

        Routine routine = Routine.create(user,request.getName());

        Set<Long> exerciseIdSet = new HashSet<>();

        for (RoutineRequest.RoutineItemDto dto : request.getRoutineItems()){
            Long exerciseId = dto.getExerciseId();

            if (!exerciseIdSet.add(exerciseId)) {
                throw new CustomException(ErrorCode.ROUTINE_ITEM_DUPLICATE);
            }

            Exercise exercise = exerciseRepository.findById(dto.getExerciseId())
                    .orElseThrow(()-> new CustomException(ErrorCode.EXERCISE_NOT_FOUND));

            RoutineItem item = RoutineItem.create(routine,exercise,dto.getSets(),dto.getReps(), dto.getWeight(), dto.getOrder());
            routine.addItem(item);
        }
        routineRepository.save(routine);
        return toRoutineResponse(routine);
    }

    private RoutineResponse toRoutineResponse(Routine routine) {
        List<RoutineResponse.RoutineItemDto> items = routine.getItems().stream()
                .map(item -> new RoutineResponse.RoutineItemDto(
                        item.getExercise().getId(),
                        item.getExercise().getName(),
                        item.getSetCount(),
                        item.getRepeatCount(),
                        item.getWeight(),
                        item.getOrderNo()
                ))
                .toList();

        return new RoutineResponse(routine.getId(), routine.getName(), items);
    }



}
