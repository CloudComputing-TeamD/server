package com.project.cloud.domain.user.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.cloud.global.auth.util.JwtParser;
import com.project.cloud.support.CustomMvcTest;
import com.project.cloud.domain.user.dto.UserInfoRequest;
import com.project.cloud.domain.user.enumerate.Gender;
import com.project.cloud.domain.user.enumerate.WorkoutLevel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@CustomMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtParser jwtParser;

    @Test
    void 유저_정보_입력() throws Exception {
        // given
        UserInfoRequest request = new UserInfoRequest(
            "email@email.com",
            Gender.FEMALE,
            160,
            50,
            LocalDate.now(),
            "목표",
            List.of("복부", "등"),
            3,
            WorkoutLevel.ADVANCED
        );

        // when & then
        String content = mockMvc.perform(post("/users/signup")
                .header("Authorization", "Bearer " + jwtParser.createToken("email@email.com"))
                .with(csrf())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString(StandardCharsets.UTF_8);
        assertThat(content).isEqualTo("ok");
    }

}
