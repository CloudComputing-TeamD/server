package com.project.cloud.domain.user.controller;

import com.project.cloud.domain.user.dto.UserCharacterResponse;
import com.project.cloud.domain.user.dto.UserInfoUpdateRequest;
import com.project.cloud.domain.user.dto.UserSignupInfoRequest;
import com.project.cloud.domain.user.service.UserService;
import com.project.cloud.global.common.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ResponseEntity<Void> signupInfo(@RequestBody UserSignupInfoRequest userSignupInfoRequest,
                                           @LoginUser String email) {
        userService.saveSignupUserInfo(userSignupInfoRequest, email);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/profile")
    public ResponseEntity<Void> updateInfo(@RequestBody UserInfoUpdateRequest userInfoUpdateRequest,
                                           @LoginUser String email) {
        userService.updateUserInfo(userInfoUpdateRequest, email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile/chracter")
    public ResponseEntity<UserCharacterResponse> userCharacterInfo(@LoginUser String email) {
        return ResponseEntity.ok(userService.getUserCharacterInfo(email));
    }
}
