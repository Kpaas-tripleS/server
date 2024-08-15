package com.tripleS.server.friend.controller;

import com.tripleS.server.friend.dto.response.GetFriendsResponse;
import com.tripleS.server.friend.service.FriendService;
import com.tripleS.server.global.dto.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/friends")
    public ResponseEntity<ResponseTemplate<Object>> getFriends(@RequestParam Long userId) {
        //토큰 아직 안해서 userId 임의로 함.
        List<GetFriendsResponse> friends = friendService.getFriends(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(friends));
    }
}
