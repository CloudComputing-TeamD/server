package com.project.cloud.domain.routineItem.entity;

import com.project.cloud.domain.exercise.entity.Exercise;
import com.project.cloud.domain.routine.entity.Routine;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ROUTINE_ITEM")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoutineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROUTINE_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ROUTINE_ID", nullable = false)
    private Routine routine;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "EXERCISE_ID", nullable = false)
    private Exercise exercise;

    @Column(name = "SETS")
    private Integer sets;

    @Column(name = "REPS")
    private Integer reps;

    @Column(name = "WEIGHT")
    private Integer weight;

    @Column(name = "ORDER_NO")
    private Integer orderNo;

    public static RoutineItem create(Exercise exercise, int sets, int reps, int weight, int orderNo) {
        return RoutineItem.builder()
                .exercise(exercise)
                .sets(sets)
                .reps(reps)
                .weight(weight)
                .orderNo(orderNo)
                .build();
    }

    public void associateRoutine(Routine routine) {
        this.routine = routine;
    }

    public void update(int sets, int reps, int weight, int orderNo) {
        this.sets    = sets;
        this.reps    = reps;
        this.weight  = weight;
        this.orderNo = orderNo;
    }
}

