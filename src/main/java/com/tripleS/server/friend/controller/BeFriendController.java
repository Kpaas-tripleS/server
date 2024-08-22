package com.tripleS.server.friend.controller;

import com.tripleS.server.friend.domain.BeFriend;
import com.tripleS.server.friend.dto.request.BeFriendRequest;
import com.tripleS.server.friend.dto.response.FriendResponseList;
import com.tripleS.server.friend.service.BeFriendService;
import com.tripleS.server.global.dto.ResponseTemplate;
import com.tripleS.server.user.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BeFriendController {

    private final BeFriendService beFriendService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/befriends")
    public ResponseTemplate<?> sendFriendRequest(@RequestHeader("access_token") String accessToken,
                                              @RequestParam String nickname) {
        Long userId = jwtTokenProvider.getUser(accessToken).userId();
        beFriendService.sendFriendRequest(userId, nickname);

        return ResponseTemplate.EMPTY_RESPONSE;
    }

    @GetMapping("/befriends")
    public ResponseTemplate<?> getFriendRequestList(@RequestHeader("access_token") String accessToken) {

        Long userId = jwtTokenProvider.getUser(accessToken).userId();
        List<String> friendRequestersList = beFriendService.getFriendRequestList(userId);

        return ResponseTemplate.from(friendRequestersList);
    }

}
