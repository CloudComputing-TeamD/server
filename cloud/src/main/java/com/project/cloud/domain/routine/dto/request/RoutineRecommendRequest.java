package com.project.cloud.domain.routine.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class RoutineRecommendRequest {
    private String goal;
    private List<String> preferredParts;
    private String level;
    private String gender;
    private Integer weight;
    private Integer top_k;
}