package com.project.cloud.domain.exercise.repository;

import com.project.cloud.domain.exercise.entity.Exercise;
import com.project.cloud.domain.exercise.enumerate.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findAllByTargetIn(List<Target> targets);
}
