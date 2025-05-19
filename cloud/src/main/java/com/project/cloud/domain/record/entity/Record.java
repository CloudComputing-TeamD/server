package com.project.cloud.domain.record.entity;

import com.project.cloud.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "RECORD")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECORD_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "TOTAL_TIME", nullable = false)
    private Integer totalTime;

    public static Record create(User user, LocalDate date, int totalTime) {
        return Record.builder()
                .user(user)
                .date(date)
                .totalTime(totalTime)
                .build();
    }
}
