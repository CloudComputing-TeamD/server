package com.project.cloud.domain.userBodyPart.entity;

import com.project.cloud.domain.bodyPart.entity.BodyPart;
import com.project.cloud.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@IdClass(UserBodyPartId.class)
@Table(name = "USER_BODY_PART")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserBodyPart {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BODY_ID", nullable = false)
    private BodyPart bodyPart;

    @Column(name = "EXP", nullable = false)
    private int exp;

    public static UserBodyPart create(User user, BodyPart bodyPart, int exp) {
        return UserBodyPart.builder()
                .user(user)
                .bodyPart(bodyPart)
                .exp(exp)
                .build();
    }

    public void updateExp(int exp) {
        this.exp = exp;
    }
}
