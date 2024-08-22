package com.tripleS.server.friend.service;

import com.tripleS.server.friend.domain.BeFriend;
import com.tripleS.server.friend.dto.request.BeFriendRequest;
import com.tripleS.server.friend.exception.FriendNotFoundException;
import com.tripleS.server.friend.exception.errorcode.FriendErrorCode;
import com.tripleS.server.friend.repository.BeFriendRepository;
import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.exception.UserNotFoundException;
import com.tripleS.server.user.exception.errorcode.UserErrorCode;
import com.tripleS.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BeFriendService {

    private final BeFriendRepository beFriendRepository;
    private final UserRepository userRepository;

    @Transactional
    public void sendFriendRequest(Long userId, String nickName) {

        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        User receiver = userRepository.findByNickname(nickName)
                .orElseThrow(() -> new FriendNotFoundException(FriendErrorCode.FRIEND_NOT_FOUND));

        BeFriendRequest friendRequest = new BeFriendRequest(
                receiver.getId(),
                requester.getId(),
                false,
                requester.getNickname()
        );

        beFriendRepository.save(friendRequest.toEntity(requester, receiver, false, requester.getNickname()));
    }

    public List<String> getFriendRequestList(Long userId) {

        User receiver = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        List<BeFriend> friendRequestList = beFriendRepository.findByReceiver(receiver);

        return friendRequestList.stream()
                .filter(beFriend -> !beFriend.isAccepted())
                .map(beFriend -> beFriend.getRequester().getNickname())
                .toList();
    }
}