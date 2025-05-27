package com.project.cloud.domain.user.dto;

import com.project.cloud.domain.user.enumerate.Frequency;
import com.project.cloud.domain.user.enumerate.Gender;
import com.project.cloud.domain.user.enumerate.Goal;
import com.project.cloud.domain.user.enumerate.WorkoutLevel;
import java.time.LocalDate;

public record UserInfoUpdateRequest(
    String name,
    Integer height,
    Integer weight,
    LocalDate birthDate,
    Gender gender,
    Goal goal,
    Frequency frequency,
    WorkoutLevel workoutLevel
) {

}
