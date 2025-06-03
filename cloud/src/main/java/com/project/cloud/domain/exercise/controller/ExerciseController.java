package com.project.cloud.domain.exercise.controller;

import com.project.cloud.domain.exercise.dto.ExerciseResponse;
import com.project.cloud.domain.exercise.enumerate.Target;
import com.project.cloud.domain.exercise.service.ExerciseService;
import com.project.cloud.global.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping("/{exerciseId}")
    public SuccessResponse<ExerciseResponse.Detail> getExercise(@PathVariable Long exerciseId) {
        return SuccessResponse.ok(exerciseService.getById(exerciseId));
    }

    @GetMapping
    @Operation(summary = "전체 운동 리스트를 조회합니다.", description = "전체 운동 리스트를 조회합니다. 쿼리 파라미터로 target이 없으면 전체 운동 리스트, 아니면 해당하는 타겟의 운동 리스트를 검색합니다.")
    public SuccessResponse<List<ExerciseResponse.Detail>> getAllExercises(@RequestParam(required = false) List<Target> target) {
        return SuccessResponse.ok(exerciseService.getAllExercises(target));
    }
}
