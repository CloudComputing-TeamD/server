package com.project.cloud.domain.bodyPart.entity;

import com.project.cloud.domain.userBodyPart.entity.UserBodyPart;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default
    @OneToMany(mappedBy = "bodyPart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserBodyPart> userBodyParts = new ArrayList<>();

    public static BodyPart create(String name) {
        return BodyPart.builder()
                .name(name)
                .build();
    }
}
