package com.project.cloud.domain.routine.entity;

import com.project.cloud.domain.routineItem.entity.RoutineItem;
import com.project.cloud.domain.user.entity.User;
import com.project.cloud.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ROUTINE")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @Builder.Default
    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoutineItem> items = new ArrayList<>();

    public static Routine create(User user, String name) {
        return Routine.builder()
                .user(user)
                .name(name)
                .build();
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void addItem(RoutineItem item) {
        items.add(item);
        item.associateRoutine(this);
    }
}
