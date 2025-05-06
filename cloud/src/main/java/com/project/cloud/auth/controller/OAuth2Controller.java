package com.project.cloud.auth.controller;

import com.project.cloud.auth.annotaion.LoginUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2Controller {

    @GetMapping("/api/me")
    public ResponseEntity<String> getMyInfo(@LoginUser String email) {
        return ResponseEntity.ok("이메일: " + email);
    }
}
