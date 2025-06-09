package com.project.cloud.domain.userBodyPart.entity;

import com.project.cloud.domain.bodyPart.entity.BodyPart;
import com.project.cloud.domain.user.entity.User;
import com.project.cloud.global.exception.CustomException;
import com.project.cloud.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

@Entity
@IdClass(UserBodyPartId.class)
@Table(name = "USER_BODY_PART")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public UserBodyPart(User user, BodyPart bodyPart, int exp) {
        if (user == null) {
            throw new CustomException(ErrorCode.USER_REQUIRED);
        }
        if (bodyPart == null) {
            throw new CustomException(ErrorCode.BODYPART_REQUIRED);
        }
        if (exp <= 0) {
            throw new CustomException(ErrorCode.USER_BODYPART_EXP_INVALID);
        }
        this.user = user;
        this.bodyPart = bodyPart;
        this.exp = exp;
    }

    public static UserBodyPart create(User user, BodyPart bodyPart, int exp) {
        return new UserBodyPart(user, bodyPart, exp);
    }

    public void saveExperience(int exp){
        this.exp += exp;
    }
}
