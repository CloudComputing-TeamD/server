package com.project.cloud.domain.user.controller;

import com.project.cloud.domain.user.dto.UserInfoRequest;
import com.project.cloud.domain.user.service.UserService;
import com.project.cloud.global.common.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signupInfo(@RequestBody UserInfoRequest userInfoRequest, @LoginUser String email) {
        return "ok";
    }
}
