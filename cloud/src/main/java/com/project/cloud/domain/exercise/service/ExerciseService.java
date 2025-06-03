package com.project.cloud.domain.exercise.service;

import com.project.cloud.domain.exercise.dto.ExerciseResponse;
import com.project.cloud.domain.exercise.entity.Exercise;
import com.project.cloud.domain.exercise.enumerate.Target;
import com.project.cloud.domain.exercise.repository.ExerciseRepository;
import com.project.cloud.global.exception.CustomException;
import com.project.cloud.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<ExerciseResponse.Detail> getAllExercises(List<Target> targetList) {
        List<Exercise> exercises;

        if (targetList == null || targetList.isEmpty()) {
            exercises = exerciseRepository.findAll();
        } else {
            exercises = exerciseRepository.findAllByTargetIn(targetList);
        }

        return exercises.stream()
                .map(ExerciseResponse.Detail::from)
                .toList();
    }
}
