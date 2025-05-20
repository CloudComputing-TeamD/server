package com.project.cloud.domain.routineItem.entity;

import com.project.cloud.domain.exercise.entity.Exercise;
import com.project.cloud.domain.routine.entity.Routine;
import com.project.cloud.global.exception.CustomException;
import com.project.cloud.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ROUTINE_ITEM")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name = "SET_COUNT")
    private Integer setCount;

    @Column(name = "REPEAT_COUNT")
    private Integer repeatCount;

    @Column(name = "WEIGHT")
    private Integer weight;

    @Column(name = "ORDER_NO")
    private Integer orderNo;

    public RoutineItem(Routine routine, Exercise exercise, Integer setCount, Integer repeatCount, Integer weight, Integer orderNo) {
        if (routine == null) {
            throw new CustomException(ErrorCode.ROUTINE_ITEM_ROUTINE_REQUIRED);
        }
        if (exercise == null) {
            throw new CustomException(ErrorCode.ROUTINE_ITEM_EXERCISE_REQUIRED);
        }
        validateCounts(setCount, repeatCount, weight, orderNo);
        this.routine = routine;
        this.exercise = exercise;
        this.setCount = setCount;
        this.repeatCount = repeatCount;
        this.weight = weight;
        this.orderNo = orderNo;
    }

    public static RoutineItem create(Routine routine, Exercise exercise, Integer setCount, Integer repeatCount, Integer weight, Integer orderNo) {
        return new RoutineItem(routine, exercise, setCount, repeatCount, weight, orderNo);
    }

    public void associateRoutine(Routine routine) {
        if (routine == null) {
            throw new CustomException(ErrorCode.ROUTINE_ITEM_ROUTINE_REQUIRED);
        }
        this.routine = routine;
    }

    public void update(Integer setCount, Integer repeatCount, Integer weight, Integer orderNo) {
        validateCounts(setCount, repeatCount, weight, orderNo);
        this.setCount    = setCount;
        this.repeatCount    = repeatCount;
        this.weight  = weight;
        this.orderNo = orderNo;
    }

    private void validateCounts(Integer setCount, Integer repeatCount, Integer weight, Integer orderNo) {
        if (setCount != null && setCount < 1) {
            throw new CustomException(ErrorCode.ROUTINE_ITEM_SETS_INVALID);
        }
        if (repeatCount != null && repeatCount < 1) {
            throw new CustomException(ErrorCode.ROUTINE_ITEM_REPS_INVALID);
        }
        if (weight != null && weight < 0) {
            throw new CustomException(ErrorCode.ROUTINE_ITEM_WEIGHT_INVALID);
        }
        if (orderNo != null && orderNo < 0) {
            throw new CustomException(ErrorCode.ROUTINE_ITEM_ORDERNO_INVALID);
        }
    }
}
