package com.tripleS.server.friend.controller;

import com.tripleS.server.friend.dto.response.FriendResponse;
import com.tripleS.server.friend.dto.response.FriendResponseList;
import com.tripleS.server.friend.service.FriendService;
import com.tripleS.server.global.dto.ResponseTemplate;
import com.tripleS.server.user.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;
    private final JwtTokenProvider jwtTokenProvider;

    //친구 목록 조회하기
    @GetMapping("/friends")
    public ResponseTemplate<?> getFriends(@RequestHeader("access_token") String accessToken) {

        Long userId = jwtTokenProvider.getUser(accessToken).userId();
        List<FriendResponse> friends = friendService.getFriendList(userId);

        return ResponseTemplate.from(FriendResponseList.from(friends));
    }

    //친구 삭제하기
    @DeleteMapping("/friends")
    public ResponseTemplate<?> deleteFriend(@RequestHeader("access_token") String accessToken,
                                            @RequestParam Long friendId) {

        Long userId = jwtTokenProvider.getUser(accessToken).userId();
        friendService.deleteFriend(userId, friendId);

        return ResponseTemplate.EMPTY_RESPONSE;
    }
}
