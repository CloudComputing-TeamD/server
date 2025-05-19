package com.project.cloud.domain.user.repository;

import com.project.cloud.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {
    User save(User email);
    Optional<User> findByEmail(String email);
}
