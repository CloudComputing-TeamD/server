package com.project.cloud.domain.user.service;

import com.project.cloud.domain.bodyPart.entity.BodyPart;
import com.project.cloud.domain.bodyPart.repository.BodyPartRepository;
import com.project.cloud.domain.exercise.enumerate.Target;
import com.project.cloud.domain.record.entity.Record;
import com.project.cloud.domain.user.dto.BodyPartExpResponse;
import com.project.cloud.domain.user.dto.DailyDurationResponse;
import com.project.cloud.domain.user.dto.UserCharacterResponse;
import com.project.cloud.domain.user.dto.UserExerciseStatisticsResponse;
import com.project.cloud.domain.user.dto.UserInfoUpdateRequest;
import com.project.cloud.domain.user.dto.UserSignupInfoRequest;
import com.project.cloud.domain.user.entity.User;
import com.project.cloud.domain.user.enumerate.Frequency;
import com.project.cloud.domain.user.repository.UserRepository;
import com.project.cloud.domain.userBodyPart.entity.UserBodyPart;
import com.project.cloud.global.exception.CustomException;
import com.project.cloud.global.exception.ErrorCode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BodyPartRepository bodyPartRepository;

    public void saveSignupUserInfo(UserSignupInfoRequest userSignupInfoRequest, String email) {
        User user = findUserByEmail(email);
        updateUser(user, userSignupInfoRequest);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXIST));
    }

    private void updateUser(User user, UserSignupInfoRequest request) {
        List<UserBodyPart> parts = getUserBodyParts(user, request);

        user.updateInfo(
            request.gender(),
            request.height(),
            request.weight(),
            request.birthDate(),
            request.goal(),
            Frequency.fromCount(request.frequency()),
            request.workoutLevel()
        );

        user.updateBodyParts(parts);
    }

    private List<UserBodyPart> getUserBodyParts(User user, UserSignupInfoRequest request) {
        return request.targetParts().stream()
            .map(partName -> {
                Optional<BodyPart> bodyPart = bodyPartRepository.findByName(partName);
                if (bodyPart.isPresent()) {
                    return new UserBodyPart(user, bodyPart.get(), 1);
                }
                throw new CustomException(ErrorCode.BODYPART_NOT_FOUND);
            })
            .toList();
    }

    public void updateUserInfo(UserInfoUpdateRequest userInfoUpdateRequest, String email) {
        User user = findUserByEmail(email);
        user.updateInfo(
            userInfoUpdateRequest.gender(),
            userInfoUpdateRequest.height(),
            userInfoUpdateRequest.weight(),
            userInfoUpdateRequest.birthDate(),
            userInfoUpdateRequest.goal(),
            userInfoUpdateRequest.frequency(),
            userInfoUpdateRequest.workoutLevel()
        );
    }

    // TODO : 이미지 저장
    public UserCharacterResponse getUserCharacterInfo(String email) {
        User user = findUserByEmail(email);
        return new UserCharacterResponse(user.getLevel(), user.getExp(), "image.url",
            makeBodyPartResponse(user.getBodyPartStats()));
    }

    private BodyPartExpResponse makeBodyPartResponse(List<UserBodyPart> bodyParts) {
        return new BodyPartExpResponse(
            findBodyPartExpByName(bodyParts, Target.CHEST),
            findBodyPartExpByName(bodyParts, Target.BACK),
            findBodyPartExpByName(bodyParts, Target.LEGS),
            findBodyPartExpByName(bodyParts, Target.CORE),
            findBodyPartExpByName(bodyParts, Target.SHOULDERS)
        );
    }

    private int findBodyPartExpByName(List<UserBodyPart> bodyParts, Target target
    ) {
        Optional<UserBodyPart> part = bodyParts.stream()
            .filter(userBodyPart -> userBodyPart.getBodyPart().getName().equals(target.name()))
            .findFirst();
        if (part.isEmpty()) {
            throw new CustomException(ErrorCode.BODYPART_NOT_FOUND);
        }
        return part.get().getExp();
    }

    public UserExerciseStatisticsResponse getUserStatistics(String email) {
        User user = findUserByEmail(email);

        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        List<Record> monthlyRecords = getRecordsInRange(user, startOfMonth, endOfMonth);
        Map<LocalDate, Integer> durationByDate = aggregateDurationsByDate(monthlyRecords);
        List<DailyDurationResponse> DailyDurationResponses = buildDailyDurationResponses(durationByDate,
            startOfMonth, endOfMonth);

        int averageDuration = calculateAverageDuration(DailyDurationResponses);
        int todayDuration = durationByDate.getOrDefault(now, 0);

        return new UserExerciseStatisticsResponse(averageDuration, todayDuration, DailyDurationResponses);
    }

    private List<Record> getRecordsInRange(User user, LocalDate start, LocalDate end) {
        return user.getRecords().stream()
            .filter(record -> !record.getDate().isBefore(start) && !record.getDate().isAfter(end))
            .toList();
    }

    private Map<LocalDate, Integer> aggregateDurationsByDate(List<Record> records) {
        Map<LocalDate, Integer> durationMap = new HashMap<>();
        for (Record record : records) {
            durationMap.merge(record.getDate(), record.getTotalTime(), Integer::sum);
        }
        return durationMap;
    }

    private List<DailyDurationResponse> buildDailyDurationResponses(Map<LocalDate, Integer> durationByDate,
                                                                    LocalDate start, LocalDate end) {
        List<DailyDurationResponse> DailyDurationResponses = new ArrayList<>();
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            int duration = durationByDate.getOrDefault(date, 0);
            DailyDurationResponses.add(new DailyDurationResponse(date, duration));
        }
        return DailyDurationResponses;
    }

    private int calculateAverageDuration(List<DailyDurationResponse> DailyDurationResponses) {
        if (DailyDurationResponses.isEmpty()) {
            return 0;
        }
        int total = DailyDurationResponses.stream().mapToInt(DailyDurationResponse::duration).sum();
        return total / DailyDurationResponses.size();
    }

}
