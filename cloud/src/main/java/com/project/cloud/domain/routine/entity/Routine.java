package com.project.cloud.domain.routine.entity;

import com.project.cloud.domain.routineItem.entity.RoutineItem;
import com.project.cloud.domain.user.entity.User;
import com.project.cloud.global.common.BaseEntity;
import com.project.cloud.global.exception.CustomException;
import com.project.cloud.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ROUTINE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Routine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROUTINE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoutineItem> items = new ArrayList<>();

    private Routine(User user, String name) {
        if (user == null) {
            throw new CustomException(ErrorCode.USER_REQUIRED);
        }
        if (name == null || name.isBlank()) {
            throw new CustomException(ErrorCode.ROUTINE_NAME_EMPTY);
        }
        this.user = user;
        this.name = name;
    }

    public static Routine create(User user, String name) {
        return new Routine(user, name);
    }

    public void updateName(String name) {
        if (name == null || name.isBlank()) {
            throw new CustomException(ErrorCode.ROUTINE_NAME_EMPTY);
        }
        this.name = name;
    }

    public void addItem(RoutineItem item) {
        if (item == null) {
            throw new CustomException(ErrorCode.ROUTINE_ITEM_REQUIRED);
        }
        items.add(item);
        item.associateRoutine(this);
    }
}
