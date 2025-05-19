package com.project.cloud.domain.exercise.entity;

import com.project.cloud.domain.exercise.enumerate.Target;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EXERCISE")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXERCISE_ID")
    private Long id;

    @Column(name = "IMAGE", length = 255)
    private String image;

    @Column(name = "LINK", length = 255)
    private String link;

    @Enumerated(EnumType.STRING)
    @Column(name = "TARGET", length = 50)
    private Target target;

    public static Exercise create(String image, String link, Target target) {
        return Exercise.builder()
                .image(image)
                .link(link)
                .target(target)
                .build();
    }

    public void updateInfo(String image, String link, Target target) {
        this.image = image;
        this.link  = link;
        this.target= target;
    }
}
