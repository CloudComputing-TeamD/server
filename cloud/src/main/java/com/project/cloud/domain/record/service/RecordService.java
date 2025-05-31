package com.project.cloud.domain.record.service;

import com.project.cloud.domain.record.dto.RecordResponse;
import com.project.cloud.domain.record.entity.Record;
import com.project.cloud.domain.record.repository.RecordRepository;
import com.project.cloud.domain.routine.entity.Routine;
import com.project.cloud.domain.routine.repository.RoutineRepository;
import com.project.cloud.domain.user.entity.User;
import com.project.cloud.domain.user.repository.UserRepository;
import com.project.cloud.global.exception.CustomException;
import com.project.cloud.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;


    @Transactional
    public RecordResponse createRecord(Long routineId, int totalTime, String email){
        User user = findUserByEmail(email);
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new CustomException(ErrorCode.ROUTINE_NOT_FOUND));

        if (!routine.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.ROUTINE_FORBIDDEN);
        }

        LocalDate today = LocalDate.now();

        Optional<Record> optinalRecord = recordRepository.findByUserAndRoutineAndDate(user, routine, today);
        Record record;
        if (optinalRecord.isPresent()){
            record = optinalRecord.get();
            int updateTime = record.getTotalTime() + totalTime;
            record.updateTotalTime(updateTime);
        }
        else {
            record = Record.create(user, routine, LocalDate.now(), totalTime);
            recordRepository.save(record);
        }


        return new RecordResponse(
                record.getId(),
                routine.getId(),
                routine.getName(),
                record.getDate(),
                record.getTotalTime()
        );
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXIST));
    }

}
