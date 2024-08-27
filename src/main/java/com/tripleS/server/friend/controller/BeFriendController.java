package com.tripleS.server.friend.controller;

import com.tripleS.server.friend.dto.request.BeFriendRequest;
import com.tripleS.server.friend.dto.response.BeFriendResponse;
import com.tripleS.server.friend.dto.response.BeFriendResponseList;
import com.tripleS.server.friend.service.BeFriendService;
import com.tripleS.server.global.dto.AuthUser;
import com.tripleS.server.global.dto.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/be-friends")
@RestController
@RequiredArgsConstructor
public class BeFriendController {


    private final BeFriendService beFriendService;

    @PostMapping("/request")
    public ResponseEntity<ResponseTemplate<?>> sendFriendRequest(@AuthenticationPrincipal AuthUser authUser,
                                                                @RequestBody BeFriendRequest beFriendRequest) {

        beFriendService.sendFriendRequest(authUser.userId(), beFriendRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @GetMapping
    public ResponseTemplate<?> getFriendRequestList(@AuthenticationPrincipal AuthUser authUser) {

        List<BeFriendResponse> friendRequestList = beFriendService.getFriendRequestList(authUser.userId());

        return ResponseTemplate.from(BeFriendResponseList.from(friendRequestList));
    }

    @PostMapping
    public ResponseEntity<ResponseTemplate<?>> handleFriendRequest(@RequestBody BeFriendRequest beFriendRequest) {

        beFriendService.handleFriendRequest(beFriendRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }
}
