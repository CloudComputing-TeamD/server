package com.project.cloud.domain.routine.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class RoutineDetailResponse {
    private String name;
    private LocalDateTime createdAt;
    private List<RoutineItemDetailDto> routineItems;

    @Getter
    @AllArgsConstructor
    public static class RoutineItemDetailDto {
        private Long exerciseId;
        private String exerciseName;
        private String target;
        private String link;
        private String image;
        private Integer sets;
        private Integer reps;
        private Integer weight;
        private Integer order;


    }
}
