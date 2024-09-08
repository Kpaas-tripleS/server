package com.tripleS.server.user.controller;

import com.tripleS.server.user.dto.response.SocialLoginResponse;
import com.tripleS.server.user.service.SocialLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SocialLoginController {

    private final SocialLoginService socialLoginService;

    @GetMapping("/loginSuccess")
    public SocialLoginResponse loginSuccess(OAuth2AuthenticationToken authentication) {
        // 액세스 토큰을 가져옴
        String accessToken = authentication.getPrincipal().getAttribute("access_token");

        // 사용자 정보를 가져옴
        SocialLoginResponse userInfo = socialLoginService.getSocialUserInfo(accessToken);

        return userInfo;
    }
}
