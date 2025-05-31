package com.project.cloud.domain.routine.controller;

import com.project.cloud.domain.routine.dto.request.RoutineRequest;
import com.project.cloud.domain.routine.dto.response.RoutineDetailResponse;
import com.project.cloud.domain.routine.dto.response.RoutineResponse;
import com.project.cloud.domain.routine.service.RoutineService;
import com.project.cloud.global.common.annotation.LoginUser;
import com.project.cloud.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/routines")
@RequiredArgsConstructor
public class RoutineController {
    private final RoutineService routineService;

    @PostMapping
    public SuccessResponse<RoutineResponse> createRoutine(@RequestBody RoutineRequest request, @LoginUser String email){
        RoutineResponse response = routineService.createRoutine(request,email);
        return SuccessResponse.ok(response);
    }
    @PutMapping("/{routineId}")
    public SuccessResponse<RoutineResponse> updateRoutine(
            @PathVariable Long routineId,
            @RequestBody RoutineRequest request,
            @LoginUser String email) {

        RoutineResponse response = routineService.updateRoutine(routineId, request, email);
        return SuccessResponse.ok(response);
    }

    @DeleteMapping("/{routineId}")
    public SuccessResponse<Void> deleteRoutine(
            @PathVariable Long routineId,
            @LoginUser String email) {

        routineService.deleteRoutine(routineId, email);
        return SuccessResponse.ok(null);
    }
    @GetMapping("/{routineId}")
    public SuccessResponse<RoutineDetailResponse> getRoutine(
            @PathVariable Long routineId,
            @LoginUser String email) {

        RoutineDetailResponse response = routineService.getRoutine(routineId, email);
        return SuccessResponse.ok(response);
    }

    @GetMapping
    public SuccessResponse<List<RoutineDetailResponse>> getAllRoutines(
            @LoginUser String email) {

        List<RoutineDetailResponse> responses = routineService.getAllRoutines(email);
        return SuccessResponse.ok(responses);
    }
}