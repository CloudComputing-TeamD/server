package com.project.cloud.domain.record.entity;

import com.project.cloud.domain.routine.entity.Routine;
import com.project.cloud.domain.user.entity.User;
import com.project.cloud.global.exception.CustomException;
import com.project.cloud.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "RECORD")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECORD_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ROUTINE_ID", nullable = false)
    private Routine routine;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "TOTAL_TIME", nullable = false)
    private Integer totalTime;

    public Record(User user, Routine routine, LocalDate date, Integer totalTime) {
        if (user == null) {
            throw new CustomException(ErrorCode.USER_REQUIRED);
        }
        if (routine == null) {
            throw new CustomException(ErrorCode.ROUTINE_REQUIRED);
        }
        if (date == null) {
            throw new CustomException(ErrorCode.RECORD_DATE_REQUIRED);
        }
        if (totalTime == null) {
            throw new CustomException(ErrorCode.RECORD_TOTALTIME_REQUIRED);
        }
        if (totalTime <= 0) {
            throw new CustomException(ErrorCode.RECORD_TOTALTIME_INVALID);
        }
        this.user = user;
        this.routine = routine;
        this.date = date;
        this.totalTime = totalTime;
    }

    public static Record create(User user, Routine routine, LocalDate date, int totalTime) {
        return new Record(user, routine, date, totalTime);

    }

    public void updateTotalTime(int updatedTime) {
        if (updatedTime <= 0) {
            throw new CustomException(ErrorCode.RECORD_TOTALTIME_INVALID);
        }
        this.totalTime = updatedTime;
    }
}
