package com.project.cloud.domain.routine.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class RoutineRequest {
    private String name;
    private List<RoutineItemDto> routineItems;

    @Getter
    public static class RoutineItemDto{
        private Long exerciseId;
        private Integer sets;
        private Integer reps;
        private Integer weight;
        private Integer order;
    }
}
