package com.project.cloud.domain.bodyPart.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BODY_PART")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BodyPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BODY_ID")
    private Long id;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    public static BodyPart create(String name) {
        return BodyPart.builder()
                .name(name)
                .build();
    }
}
