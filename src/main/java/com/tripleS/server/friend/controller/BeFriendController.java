package com.tripleS.server.friend.controller;

import com.tripleS.server.friend.service.BeFriendService;
import com.tripleS.server.global.dto.ResponseTemplate;
import com.tripleS.server.user.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BeFriendController {

    private final BeFriendService beFriendService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/Befriends")
    public ResponseTemplate<?> sendFriendRequest(@RequestHeader("access_token") String accessToken,
                                              @RequestParam String nickname) {
        Long userId = jwtTokenProvider.getUser(accessToken).userId();
        beFriendService.sendFriendRequest(userId, nickname);

        return ResponseTemplate.EMPTY_RESPONSE;
    }
}
