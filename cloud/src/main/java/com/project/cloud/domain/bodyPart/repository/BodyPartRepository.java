package com.project.cloud.domain.bodyPart.repository;

import com.project.cloud.domain.bodyPart.entity.BodyPart;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface BodyPartRepository extends Repository<BodyPart, Long> {

    Optional<BodyPart> findByName(String name);

    BodyPart save(BodyPart bodyPart);
}
