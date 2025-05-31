package com.project.cloud.domain.routine.repository;

import com.project.cloud.domain.routine.entity.Routine;
import com.project.cloud.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Long> {
    boolean existsByUserAndName(User user, String name);

}
