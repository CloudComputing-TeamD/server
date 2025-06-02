package com.project.cloud.domain.exercise.service;

import com.project.cloud.domain.exercise.dto.ExerciseResponse;
import com.project.cloud.domain.exercise.entity.Exercise;
import com.project.cloud.domain.exercise.repository.ExerciseRepository;
import com.project.cloud.global.exception.CustomException;
import com.project.cloud.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseResponse.Detail getById(Long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new CustomException(ErrorCode.EXERCISE_NOT_FOUND));

        return ExerciseResponse.Detail.from(exercise);
    }
}
