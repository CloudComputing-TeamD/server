package com.project.cloud.domain.user.entity;

import com.project.cloud.domain.record.entity.Record;
import com.project.cloud.domain.routine.entity.Routine;
import com.project.cloud.domain.user.enumerate.Frequency;
import com.project.cloud.domain.user.enumerate.Gender;
import com.project.cloud.domain.user.enumerate.Goal;
import com.project.cloud.domain.user.enumerate.WorkoutLevel;
import com.project.cloud.domain.userBodyPart.entity.UserBodyPart;
import com.project.cloud.global.common.BaseEntity;
import com.project.cloud.global.exception.CustomException;
import com.project.cloud.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 6)
    private Gender gender;

    @Column
    private Integer height;

    @Column
    private Integer weight;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Goal goal;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Frequency frequency;

    @Enumerated(EnumType.STRING)
    @Column(name = "WORKOUT_LEVEL", length = 12)
    private WorkoutLevel workoutLevel;

    @Column(nullable = false)
    private int level = 0; // 캐릭터 단계 (0~5), 기본 0

    @Column(nullable = false)
    private int exp = 0; // 경험치, 기본 0

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Routine> routines = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserBodyPart> bodyPartStats = new ArrayList<>();


    public User(String email, String name) {
        if (email == null || email.isBlank()) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }
        if (name == null || name.isBlank()) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }
        this.email = email;
        this.name = name;
    }

    public static User create(String email, String name) {
        return new User(email, name);
    }

}
