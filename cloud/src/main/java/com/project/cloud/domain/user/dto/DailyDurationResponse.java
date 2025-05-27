package com.project.cloud.domain.user.dto;

import java.time.LocalDate;

public record DailyDurationResponse(
    LocalDate date,
    int duration
) {

}
