package com.project.cloud.auth.infrastructure;

import com.project.cloud.auth.util.JwtParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtParser jwtParser;

    public OAuth2LoginSuccessHandler(JwtParser jwtParser) {
        this.jwtParser = jwtParser;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        String email = user.getAttribute("email");

        String token = jwtParser.createToken(email);

        response.setHeader("Authorization", "Bearer " + token);
        response.sendRedirect("http://localhost:3000/");

    }
}
