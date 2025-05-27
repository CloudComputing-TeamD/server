package com.project.cloud.domain.user.dto;

import com.project.cloud.domain.user.enumerate.Gender;
import com.project.cloud.domain.user.enumerate.Goal;
import com.project.cloud.domain.user.enumerate.WorkoutLevel;
import java.time.LocalDate;
import java.util.List;

public record UserSignupInfoRequest(
    String email,
    Gender gender,
    int height,
    int weight,
    LocalDate birthDate,
    Goal goal,
    List<String> targetParts,
    int frequency,
    WorkoutLevel workoutLevel
) {

}
