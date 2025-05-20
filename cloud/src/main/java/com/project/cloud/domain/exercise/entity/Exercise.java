package com.project.cloud.domain.exercise.entity;

import com.project.cloud.domain.exercise.enumerate.Target;
import com.project.cloud.global.exception.CustomException;
import com.project.cloud.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EXERCISE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXERCISE_ID")
    private Long id;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "IMAGE", length = 255)
    private String image;

    @Column(name = "LINK", length = 255)
    private String link;

    @Enumerated(EnumType.STRING)
    @Column(name = "TARGET", length = 50)
    private Target target;

    public Exercise(String name, String image, String link, Target target) {
        if (name == null || name.isBlank()) {
            throw new CustomException(ErrorCode.EXERCISE_NAME_EMPTY);
        }
        this.name = name;
        this.image = image;
        this.link = link;
        this.target = target;
    }

    public static Exercise create(String name, String image, String link, Target target) {
        return new Exercise(name, image, link, target);
    }
}
