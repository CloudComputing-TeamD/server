package com.project.cloud.global.auth.service.command;

import com.project.cloud.domain.user.entity.User;
import com.project.cloud.domain.user.repository.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthCommandService {

    private final UserRepository userRepository;

    public AuthCommandService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User makeUser(String email, String name) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            return userRepository.save(new User(email, name));
        }
        return user.get();
    }
}
