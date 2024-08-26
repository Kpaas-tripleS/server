package com.tripleS.server.friend.service;

import com.tripleS.server.friend.domain.Friend;
import com.tripleS.server.friend.dto.request.BeFriendRequest;
import com.tripleS.server.friend.dto.response.BeFriendResponse;
import com.tripleS.server.friend.exception.FriendNotFoundException;
import com.tripleS.server.friend.exception.errorcode.FriendErrorCode;
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

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;


    @Transactional
    public void sendFriendRequest(Long userId, BeFriendRequest beFriendRequest) {

        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
        User receiver = userRepository.findById(beFriendRequest.receiver())
                .orElseThrow(() -> new FriendNotFoundException(FriendErrorCode.FRIEND_NOT_FOUND));

        friendRepository.save(beFriendRequest.toEntity(requester, receiver, true));
    }

    public List<BeFriendResponse> getFriendRequestList(Long userId) {

        User receiver = userRepository.findByIdFetchBeFriend(userId)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        return receiver.getFriendList().stream()
                .filter(Friend -> !Friend.getIsAccepted())
                .map(BeFriendResponse::from)
                .toList();
    }

    @Transactional
    public void acceptFriendRequest(Long userId, BeFriendRequest beFriendRequest) {

        User receiver = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        User requester = userRepository.findById(beFriendRequest.requester())
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        friendRepository.findByUserIdAndFriendId(receiver.getId(), requester.getId())
                .ifPresentOrElse(friend -> {
                    beFriendRequest.toEntity(requester, receiver, true);
                    friendRepository.save(friend);
                }, () -> {
                    throw new FriendNotFoundException(FriendErrorCode.FRIEND_NOT_FOUND);
                });

    }

    @Transactional
    public void rejectFriendRequest(BeFriendRequest beFriendRequest) {

        Friend friend = friendRepository.findByUserIdAndFriendId(
                        beFriendRequest.receiver(), beFriendRequest.requester())
                .orElseThrow(() -> new FriendNotFoundException(FriendErrorCode.FRIEND_NOT_FOUND));

        friendRepository.delete(friend);
    }
}