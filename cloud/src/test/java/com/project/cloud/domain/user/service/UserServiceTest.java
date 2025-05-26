package com.project.cloud.domain.user.service;

import static org.assertj.core.api.Assertions.*;

import com.project.cloud.domain.bodyPart.entity.BodyPart;
import com.project.cloud.domain.bodyPart.repository.BodyPartRepository;
import com.project.cloud.domain.user.dto.UserInfoRequest;
import com.project.cloud.domain.user.entity.User;
import com.project.cloud.domain.user.enumerate.Gender;
import com.project.cloud.domain.user.enumerate.Goal;
import com.project.cloud.domain.user.enumerate.WorkoutLevel;
import com.project.cloud.domain.user.repository.UserRepository;
import com.project.cloud.domain.userBodyPart.entity.UserBodyPart;
import com.project.cloud.global.exception.CustomException;
import com.project.cloud.global.exception.ErrorCode;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BodyPartRepository bodyPartRepository;

    private final String email = "test@email.com";

    @BeforeEach
    void setUp() {
        // 사용자와 BodyPart 사전 저장
        userRepository.save(new User(email, "홍길동"));
        bodyPartRepository.save(BodyPart.create("복부"));
        bodyPartRepository.save(BodyPart.create("등"));
    }

    @Test
    void saveSignupUserInfo_성공() {
        // given
        UserInfoRequest request = new UserInfoRequest(
            email,
            Gender.MALE,
            180,
            75,
            LocalDate.of(1990, 1, 1),
            "GAIN_MUSCLE",
            List.of("복부", "등"),
            3,
            WorkoutLevel.BEGINNER
        );

        // when
        userService.saveSignupUserInfo(request, email);

        // then
        User updated = userRepository.findByEmail(email).orElseThrow();
        assertThat(updated.getGender()).isEqualTo(Gender.MALE);
        assertThat(updated.getGoal()).isEqualTo(Goal.GAIN_MUSCLE);
        assertThat(updated.getBodyPartStats()).extracting(UserBodyPart::getBodyPart)
            .extracting(BodyPart::getName)
            .containsExactlyInAnyOrder("복부", "등");
    }

    @Test
    void saveSignupUserInfo_BodyPart_없으면_예외() {
        // given: "가슴"은 DB에 없음
        UserInfoRequest request = new UserInfoRequest(
            email,
            Gender.MALE,
            180,
            75,
            LocalDate.of(1990, 1, 1),
            "GAIN_MUSCLE",
            List.of("복부", "가슴"), // 가슴은 DB에 없음
            3,
            WorkoutLevel.BEGINNER
        );

        // when & then
        assertThatThrownBy(() -> userService.saveSignupUserInfo(request, email))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.BODYPART_NOT_FOUND.getMessage());
    }
}
