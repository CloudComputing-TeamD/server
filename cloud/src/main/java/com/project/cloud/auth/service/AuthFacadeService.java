package com.project.cloud.auth.service;

import com.project.cloud.auth.service.command.AuthCommandService;
import java.util.Map;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class AuthFacadeService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final AuthCommandService authCommandService;

    public AuthFacadeService(AuthCommandService authCommandService) {
        this.authCommandService = authCommandService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        Map<String, Object> attributes = oauth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        authCommandService.makeUser(email, name);
        return oauth2User;
    }
}
