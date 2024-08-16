package com.tripleS.server.friend.controller;

import com.tripleS.server.friend.dto.response.FriendResponse;
import com.tripleS.server.friend.dto.response.FriendResponseList;
import com.tripleS.server.friend.service.FriendService;
import com.tripleS.server.global.dto.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    //친구 목록 조회하기
    @GetMapping("/friends")
    public ResponseTemplate<?> getFriends(@RequestParam Long userId) {

        //토큰 아직 안해서 userId 임의로 함.
        List<FriendResponse> friends = friendService.getFriendList(userId);

        return ResponseTemplate.from(FriendResponseList.from(friends));
    }

    //친구 삭제하기
    @DeleteMapping("/friends")
    public ResponseTemplate<?> deleteFriend(@RequestParam Long userId, @RequestParam Long friendId) {

        friendService.deleteFriend(userId, friendId);

        return ResponseTemplate.EMPTY_RESPONSE;
    }
}
