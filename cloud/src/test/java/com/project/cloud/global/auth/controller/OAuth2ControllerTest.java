package com.project.cloud.global.auth.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.cloud.global.auth.util.JwtParser;
import com.project.cloud.support.CustomMvcTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@CustomMvcTest
class OAuth2ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtParser jwtParser;

    @Test
    void 내_정보_조회_테스트() throws Exception {
        mockMvc.perform(get("/api/me")
                .header("Authorization", "Bearer " + jwtParser.createToken("test@example.com")))
            .andExpect(status().isOk())
            .andExpect(content().string("이메일: test@example.com"));
    }

}
