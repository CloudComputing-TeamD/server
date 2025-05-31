package com.project.cloud.domain.record.controller;


import com.project.cloud.domain.record.dto.RecordResponse;
import com.project.cloud.domain.record.service.RecordService;
import com.project.cloud.global.common.annotation.LoginUser;
import com.project.cloud.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @PostMapping
    public SuccessResponse<RecordResponse> createRecord(
            @RequestParam Long routineId,
            @RequestParam int totalTime,
            @LoginUser String email) {


        RecordResponse response = recordService.createRecord(routineId, totalTime, email);
        return SuccessResponse.ok(response);
    }
    @GetMapping
    public SuccessResponse<List<RecordResponse>> getRecordsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @LoginUser String email) {

        List<RecordResponse> response = recordService.getRecordsByDate(email, date);
        return SuccessResponse.ok(response);
    }

}