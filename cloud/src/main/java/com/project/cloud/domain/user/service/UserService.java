package com.project.cloud.domain.user.service;

import com.project.cloud.domain.bodyPart.entity.BodyPart;
import com.project.cloud.domain.bodyPart.repository.BodyPartRepository;
import com.project.cloud.domain.user.dto.UserInfoRequest;
import com.project.cloud.domain.user.entity.User;
import com.project.cloud.domain.user.enumerate.Frequency;
import com.project.cloud.domain.user.enumerate.Gender;
import com.project.cloud.domain.user.enumerate.Goal;
import com.project.cloud.domain.user.enumerate.WorkoutLevel;
import com.project.cloud.domain.user.repository.UserRepository;
import com.project.cloud.domain.userBodyPart.entity.UserBodyPart;
import com.project.cloud.global.exception.CustomException;
import com.project.cloud.global.exception.ErrorCode;
import java.time.LocalDate;
import java.util.List;
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

    public void saveSignupUserInfo(UserInfoRequest userInfoRequest, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXIST));
        updateUser(user, userInfoRequest);
    }

    private void updateUser(User user, UserInfoRequest request) {
        Gender gender = request.gender();
        Integer height = request.height();
        Integer weight = request.weight();
        LocalDate birthDate = request.birthDate();
        Goal goal = Goal.valueOf(request.goal().toUpperCase());
        Frequency frequency = Frequency.fromCount(request.frequency());
        WorkoutLevel workoutLevel = request.workoutLevel();

        List<UserBodyPart> parts = request.targetParts().stream()
            .map(partName -> {
                Optional<BodyPart> bodyPart = bodyPartRepository.findByName(partName);
                if (bodyPart.isPresent()) {
                    return new UserBodyPart(user, bodyPart.get(), 1);
                }
                throw new CustomException(ErrorCode.BODYPART_NOT_FOUND);
            })
            .toList();

        user.updateInfo(gender, height, weight, birthDate, goal, frequency, workoutLevel, parts);
    }
}
