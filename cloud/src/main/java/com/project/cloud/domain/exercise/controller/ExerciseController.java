package com.project.cloud.domain.exercise.controller;

import com.project.cloud.domain.exercise.dto.ExerciseResponse;
import com.project.cloud.domain.exercise.service.ExerciseService;
import com.project.cloud.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping("/{exerciseId}")
    public SuccessResponse<ExerciseResponse.Detail> getExercise(@PathVariable Long exerciseId) {
        return SuccessResponse.ok(exerciseService.getById(exerciseId));
    }
}
