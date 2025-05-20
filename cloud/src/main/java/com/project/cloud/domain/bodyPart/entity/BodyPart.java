package com.project.cloud.domain.bodyPart.entity;

import com.project.cloud.global.exception.CustomException;
import com.project.cloud.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BODY_PART")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BodyPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BODY_ID")
    private Long id;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    private BodyPart(String name) {
        if (name == null || name.isBlank()) {
            throw new CustomException(ErrorCode.BODYPART_NAME_EMPTY);
        }
        this.name = name;
    }

    public static BodyPart create(String name) {
        return new BodyPart(name);
    }
}
