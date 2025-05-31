package com.project.cloud.domain.record.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RecordResponse {
    private Long recordId;
    private Long routineId;
    private String routineName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private int totalTime;
}