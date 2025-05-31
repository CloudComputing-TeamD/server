package com.project.cloud.domain.routine.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class RoutineResponse {
    private final Long routineId;
    private final String name;
    private final List<RoutineItemDto> routineItems;

    public RoutineResponse(Long routineId, String name, List<RoutineItemDto> routineItems) {
        this.routineId = routineId;
        this.name = name;
        this.routineItems = routineItems;
    }

    @Getter
    public static class RoutineItemDto {
        private final Long exerciseId;
        private final String exerciseName;
        private final Integer sets;
        private final Integer reps;
        private final Integer weight;
        private final Integer order;

        public RoutineItemDto(Long exerciseId, String exerciseName, Integer sets, Integer reps, Integer weight, Integer order) {
            this.exerciseId = exerciseId;
            this.exerciseName = exerciseName;
            this.sets = sets;
            this.reps = reps;
            this.weight = weight;
            this.order = order;
        }
    }
}