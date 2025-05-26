package com.project.cloud.support;

import com.project.cloud.global.auth.util.JwtParser;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestBeanConfig {

    @Bean
    public JwtParser jwtParser() {
        return new JwtParser("c1a2b3c4d5e6f7g8h9i0j1k2l3m4n5o6");
    }
}
