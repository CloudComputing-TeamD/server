package com.project.cloud.domain.exercise.dto;

import com.project.cloud.domain.exercise.entity.Exercise;
import com.project.cloud.domain.exercise.enumerate.Target;

public class ExerciseResponse {

    public record Detail(
            Long exerciseId,
            String name,
            Target target,
            Integer baseWeight,
            String type,
            String level,
            String imageUrl,
            String link
    ) {
        public static Detail from(Exercise exercise) {
            return new Detail(
                    exercise.getId(),
                    exercise.getName(),
                    exercise.getTarget(),
                    exercise.getBaseWeight(),
                    exercise.getType(),
                    exercise.getLevel(),
                    exercise.getImage(),
                    exercise.getLink()
            );
        }
    }
}
