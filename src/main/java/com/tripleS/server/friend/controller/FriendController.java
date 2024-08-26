package com.tripleS.server.friend.controller;

import com.tripleS.server.friend.dto.request.BeFriendRequest;
import com.tripleS.server.friend.dto.response.FriendResponse;
import com.tripleS.server.friend.dto.response.FriendResponseList;
import com.tripleS.server.friend.service.FriendService;
import com.tripleS.server.global.dto.AuthUser;
import com.tripleS.server.global.dto.ResponseTemplate;
import com.tripleS.server.user.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/friends")
    public ResponseTemplate<?> getFriends(@AuthenticationPrincipal AuthUser authUser) {

        List<FriendResponse> friends = friendService.getFriendList(authUser.userId());

        return ResponseTemplate.from(FriendResponseList.from(friends));
    }

    @DeleteMapping("/friends")
    public ResponseTemplate<?> deleteFriend(@AuthenticationPrincipal AuthUser authUser,
                                            @RequestParam Long friendId) {

        friendService.deleteFriend(authUser.userId(), friendId);

        return ResponseTemplate.EMPTY_RESPONSE;
    }
}
