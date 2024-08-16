package com.tripleS.server.friend.service;

import com.tripleS.server.friend.dto.response.FriendResponse;
import com.tripleS.server.friend.exception.FriendNotFoundException;
import com.tripleS.server.friend.exception.errorcode.FriendErrorCode;
import com.tripleS.server.friend.repository.FriendRepository;
import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public List<FriendResponse> getFriendList(Long userId) {

        User user = userRepository.findByIdFetchFriend(userId)
                .orElseThrow(() -> new FriendNotFoundException(FriendErrorCode.FRIEND_NOT_FOUND));

        user.getFriendList().forEach(friend -> log.info("friend: {}", friend.getFriend().getNickname()));

        return user.getFriendList().stream()
                .map(FriendResponse::from)
                .toList();
    }

    @Transactional
    public void deleteFriend(Long userId, Long friendId) {

        friendRepository.findByUserIdAndFriendId(userId, friendId)
                .ifPresentOrElse(
                        friendRepository::delete,
                        () -> {
                            throw new FriendNotFoundException(FriendErrorCode.FRIEND_NOT_FOUND);
                        });
    }
}
//friendservice
