package com.project.cloud.domain.record.service;

import com.project.cloud.domain.exercise.enumerate.Target;
import com.project.cloud.domain.record.dto.RecordResponse;
import com.project.cloud.domain.record.entity.Record;
import com.project.cloud.domain.record.repository.RecordRepository;
import com.project.cloud.domain.routine.entity.Routine;
import com.project.cloud.domain.routine.repository.RoutineRepository;
import com.project.cloud.domain.routineItem.entity.RoutineItem;
import com.project.cloud.domain.user.entity.User;
import com.project.cloud.domain.user.repository.UserRepository;
import com.project.cloud.domain.userBodyPart.entity.UserBodyPart;
import com.project.cloud.global.exception.CustomException;
import com.project.cloud.global.exception.ErrorCode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RecordService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final RecordRepository recordRepository;
    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;


    @Transactional
    public RecordResponse createRecord(Long routineId, int totalTime, String email) {
        User user = findUserByEmail(email);
        Routine routine = routineRepository.findById(routineId)
            .orElseThrow(() -> new CustomException(ErrorCode.ROUTINE_NOT_FOUND));

        if (!routine.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.ROUTINE_FORBIDDEN);
        }

        saveExperience(user, routine);

        LocalDate today = LocalDate.now();

        Optional<Record> optinalRecord = recordRepository.findByUserAndRoutineAndDate(user, routine, today);
        Record record;
        if (optinalRecord.isPresent()) {
            record = optinalRecord.get();
            int updateTime = record.getTotalTime() + totalTime;
            record.updateTotalTime(updateTime);
        } else {
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

    private void saveExperience(User user, Routine routine) {
        for (RoutineItem item : routine.getItems()) {
            saveUserBodyExperience(user, item);
        }
    }

    private void saveUserBodyExperience(User user, RoutineItem item) {
        UserBodyPart userBodyPart = findUserBodyPartByTarget(user.getBodyPartStats(),
            item.getExercise().getTarget());
        int exp = calculateExp(item);
        userBodyPart.saveExperience(exp);
        user.saveExperience(exp);
    }

    private UserBodyPart findUserBodyPartByTarget(List<UserBodyPart> bodyPartStats, Target target) {
        for (UserBodyPart bodyPartStat : bodyPartStats) {
            String bodyPartName = bodyPartStat.getBodyPart().getName();
            if (bodyPartName.equals(target.name())) {
                return bodyPartStat;
            }
        }
        throw new CustomException(ErrorCode.BODYPART_NOT_FOUND);
    }

    private int calculateExp(RoutineItem item) {
        return item.getSetCount() * item.getRepeatCount();
    }


    @Transactional(readOnly = true)
    public List<RecordResponse> getRecordsByDate(String email, LocalDate date) {
        User user = findUserByEmail(email);
        List<Record> records = recordRepository.findAllByUserAndDate(user, date);
        return records.stream()
            .map(this::toRecordResponse)
            .toList();
    }


    private RecordResponse toRecordResponse(Record record) {
        return new RecordResponse(
            record.getId(),
            record.getRoutine().getId(),
            record.getRoutine().getName(),
            record.getDate(),
            record.getTotalTime()
        );
    }


    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXIST));
    }

}
