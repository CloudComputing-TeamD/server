package com.project.cloud.domain.user.dto;

import java.util.List;

public record UserExerciseStatisticsResponse(
    int averageDuration,
    int todayDuration,
    List<DailyDurationResponse> dailyDurationResponses
) {

}
