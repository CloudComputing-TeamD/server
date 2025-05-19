package com.project.cloud.domain.userBodyPart.entity;

import java.io.Serializable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBodyPartId implements Serializable {
    private Long user;      // User.id 와 매칭
    private Long bodyPart;  // BodyPart.id 와 매칭
}
