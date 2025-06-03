package com.project.cloud.domain.routine.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class RoutineRequest {
    private final String name;
    private final List<RoutineItemDto> routineItems;

    public RoutineRequest(String name, List<RoutineItemDto> routineItems) {
        this.name = name;
        this.routineItems = routineItems;
    }

    @Getter
    public static class RoutineItemDto{
        private final Long exerciseId;
        private final Integer sets;
        private final Integer reps;
        private final Integer weight;
        private final Integer order;

        public RoutineItemDto(Long exerciseId, Integer sets, Integer reps, Integer weight, Integer order) {
            this.exerciseId = exerciseId;
            this.sets = sets;
            this.reps = reps;
            this.weight = weight;
            this.order = order;
        }
    }
}
