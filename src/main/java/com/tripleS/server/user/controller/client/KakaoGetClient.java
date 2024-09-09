package com.tripleS.server.user.controller.client;

import com.tripleS.server.user.dto.response.SocialLoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "kakaoGetClient", url = "${kakao.feign.api-base-url}")
public interface KakaoGetClient {
    //카카오톡 서버에서 사용자 정보가져오는 api
    @GetMapping("/v2/user/me")
    SocialLoginResponse getUserInfo(@RequestParam("access_token") String accessToken);
}
