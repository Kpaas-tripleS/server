package com.tripleS.server.user.service;

import com.tripleS.server.user.controller.client.KakaoClient;
import com.tripleS.server.user.controller.client.KakaoGetClient;
import com.tripleS.server.user.dto.JwtTokenDto;
import com.tripleS.server.user.dto.response.SocialLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SocialLoginService {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    private final KakaoClient kakaoClient;
    private final KakaoGetClient kakaoGetClient;

    public String getAccessToken(String code) {

        JwtTokenDto accessToken = kakaoClient.getAccessToken("authorization_code", clientId, redirectUri, code);
        return accessToken.accessToken();
    }

    public SocialLoginResponse getSocialUserInfo(String accessToken) {
        return kakaoGetClient.getUserInfo("Bearer " + accessToken);
    }
}