package com.project.cloud.domain.exercise.entity;

import com.project.cloud.domain.exercise.enumerate.Target;
import com.project.cloud.global.exception.CustomException;
import com.project.cloud.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exercises")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "target", length = 50)
    private Target target;

    @Column(name = "base_weight")
    private Integer baseWeight;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "level", length = 50)
    private String level;

    @Column(name = "image", length = 255)
    private String image;

    @Column(name = "link", length = 255)
    private String link;

    public Exercise(String name, Target target, Integer baseWeight, String type, String level, String image, String link) {
        if (name == null || name.isBlank()) {
            throw new CustomException(ErrorCode.EXERCISE_NAME_EMPTY);
        }
        this.name = name;
        this.target = target;
        this.baseWeight = baseWeight;
        this.type = type;
        this.level = level;
        this.image = image;
        this.link = link;
    }

    public static Exercise create(String name, Target target, Integer baseWeight, String type, String level, String image, String link) {
        return new Exercise(name, target, baseWeight, type, level, image, link);
    }
}
