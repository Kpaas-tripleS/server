package com.tripleS.server.friend.controller;

import com.tripleS.server.friend.dto.request.BeFriendRequest;
import com.tripleS.server.friend.dto.response.BeFriendResponse;
import com.tripleS.server.friend.dto.response.BeFriendResponseList;
import com.tripleS.server.friend.service.BeFriendService;
import com.tripleS.server.global.dto.AuthUser;
import com.tripleS.server.global.dto.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/be-friends")
@RestController
@RequiredArgsConstructor
public class BeFriendController {


    private final BeFriendService beFriendService;

    @PostMapping("/be-friends")
    public ResponseTemplate<?> sendFriendRequest(@AuthenticationPrincipal AuthUser authUser,
                                                 @RequestBody BeFriendRequest beFriendRequest) {

        beFriendService.sendFriendRequest(authUser.userId(), beFriendRequest);

        return ResponseTemplate.EMPTY_RESPONSE;
    }

    @GetMapping
    public ResponseTemplate<?> getFriendRequestList(@AuthenticationPrincipal AuthUser authUser) {

        List<BeFriendResponse> friendRequestList = beFriendService.getFriendRequestList(authUser.userId());

        return ResponseTemplate.from(BeFriendResponseList.from(friendRequestList));
    }

    @PostMapping("/accept")
    public ResponseTemplate<?> acceptFriendRequest(@AuthenticationPrincipal AuthUser authUser,
                                                  @RequestBody BeFriendRequest beFriendRequest) {

        beFriendService.acceptFriendRequest(authUser.userId(), beFriendRequest);

        return ResponseTemplate.EMPTY_RESPONSE;
    }

    @DeleteMapping("/accept")
    public ResponseTemplate<?> rejectFriendRequest(@AuthenticationPrincipal AuthUser authUser,
                                                  @RequestBody BeFriendRequest beFriendRequest) {

        beFriendService.rejectFriendRequest(beFriendRequest);

        return ResponseTemplate.EMPTY_RESPONSE;
    }
}
