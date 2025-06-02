package com.project.cloud.domain.exercise.dto;

import com.project.cloud.domain.exercise.entity.Exercise;
import com.project.cloud.domain.exercise.enumerate.Target;

public class ExerciseResponse {

    public record Detail(
            Long exerciseId,
            String name,
            String imageUrl,
            String link,
            Target target
    ) {
        public static Detail from(Exercise exercise) {
            return new Detail(
                    exercise.getId(),
                    exercise.getName(),
                    exercise.getImage(),
                    exercise.getLink(),
                    exercise.getTarget()
            );
        }
    }
}
