package com.tripleS.server.friend.service;

import com.tripleS.server.friend.domain.BeFriend;
import com.tripleS.server.friend.domain.Friend;
import com.tripleS.server.friend.dto.request.BeFriendRequest;
import com.tripleS.server.friend.dto.response.BeFriendResponse;
import com.tripleS.server.friend.exception.FriendNotFoundException;
import com.tripleS.server.friend.exception.errorcode.FriendErrorCode;
import com.tripleS.server.friend.repository.BeFriendRepository;
import com.tripleS.server.friend.repository.FriendRepository;
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
    private final FriendRepository friendRepository;

    @Transactional
    public void sendFriendRequest(Long userId, BeFriendRequest beFriendRequest) {

        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
        User receiver = userRepository.findById(beFriendRequest.receiver())
                .orElseThrow(() -> new FriendNotFoundException(FriendErrorCode.FRIEND_NOT_FOUND));

        beFriendRepository.save(beFriendRequest.toEntity(requester, receiver));
    }

    public List<BeFriendResponse> getFriendRequestList(Long userId) {

        User receiver = userRepository.findByIdFetchBeFriend(userId)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        return receiver.getBeFriendList().stream()
                .map(BeFriendResponse::from)
                .toList();
    }

    @Transactional
    public void acceptFriendRequest(Long userId, BeFriendResponse beFriendResponse) {

        User receiver = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        User requester = userRepository.findById(beFriendResponse.requesterId())
              .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        BeFriend beFriend = beFriendRepository.findById(beFriendResponse.beFriendId())
                .orElseThrow(() -> new FriendNotFoundException(FriendErrorCode.FRIEND_NOT_FOUND));

        beFriendRepository.delete(beFriend);

        Friend friend = Friend.builder()
                .user(receiver)
                .friend(requester)
                .build();

        friendRepository.save(friend);
    }

    @Transactional
    public void rejectFriendRequest(BeFriendResponse beFriendResponse) {

        BeFriend beFriend = beFriendRepository.findById(beFriendResponse.beFriendId())
                .orElseThrow(() -> new FriendNotFoundException(FriendErrorCode.FRIEND_NOT_FOUND));

        beFriendRepository.delete(beFriend);
    }
}