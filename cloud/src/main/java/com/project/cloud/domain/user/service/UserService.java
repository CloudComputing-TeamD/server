package com.project.cloud.domain.user.service;

import com.project.cloud.domain.bodyPart.entity.BodyPart;
import com.project.cloud.domain.bodyPart.repository.BodyPartRepository;
import com.project.cloud.domain.user.dto.BodyPartExpResponse;
import com.project.cloud.domain.user.dto.UserCharacterResponse;
import com.project.cloud.domain.user.dto.UserInfoUpdateRequest;
import com.project.cloud.domain.user.dto.UserSignupInfoRequest;
import com.project.cloud.domain.user.entity.User;
import com.project.cloud.domain.user.enumerate.Frequency;
import com.project.cloud.domain.user.repository.UserRepository;
import com.project.cloud.domain.userBodyPart.entity.UserBodyPart;
import com.project.cloud.global.exception.CustomException;
import com.project.cloud.global.exception.ErrorCode;
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

    // TODO : bodyPart Enum 작성 필요
    private BodyPartExpResponse makeBodyPartResponse(List<UserBodyPart> bodyParts) {
        return new BodyPartExpResponse(
            findBodyPartExpByName(bodyParts, "chest"),
            findBodyPartExpByName(bodyParts, "back"),
            findBodyPartExpByName(bodyParts, "legs"),
            findBodyPartExpByName(bodyParts, "abs"),
            findBodyPartExpByName(bodyParts, "shoulders")
        );
    }

    // TODO : N+1 없애기
    private int findBodyPartExpByName(List<UserBodyPart> bodyParts, String partName) {
        Optional<UserBodyPart> part = bodyParts.stream()
            .filter(userBodyPart -> userBodyPart.getBodyPart().getName().equals(partName))
            .findFirst();
        if (part.isEmpty()) {
            throw new CustomException(ErrorCode.BODYPART_NOT_FOUND);
        }
        return part.get().getExp();
    }
}
