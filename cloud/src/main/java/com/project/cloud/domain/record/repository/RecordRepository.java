package com.project.cloud.domain.record.repository;

import com.project.cloud.domain.record.entity.Record;
import com.project.cloud.domain.routine.entity.Routine;
import com.project.cloud.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    Optional<Record> findByUserAndRoutineAndDate(User user, Routine routine, LocalDate date);
    List<Record> findAllByUserAndDate(User user, LocalDate date);

}
