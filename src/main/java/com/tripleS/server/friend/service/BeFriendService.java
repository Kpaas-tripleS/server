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

        friendRepository.save(beFriendRequest.toEntity(requester, receiver, false));
    }

    public List<BeFriendResponse> getFriendRequestList(Long userId) {

        List<Friend> friendsList = friendRepository.findByUserIdAndIsAcceptedFalse(userId);

        return friendsList.stream()
                .map(friend -> {
                    return BeFriendResponse.from(friend);
                })
                .toList();
    }

    @Transactional
    public void acceptFriendRequest(Long userId, BeFriendRequest beFriendRequest) {

        User receiver = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        User requester = userRepository.findById(beFriendRequest.requester())
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        Friend friend = friendRepository.findByUserIdAndFriendId(receiver.getId(), requester.getId())
                .orElseThrow(() -> new FriendNotFoundException(FriendErrorCode.FRIEND_NOT_FOUND));

        friend.accept();

        friendRepository.save(friend);
    }

    @Transactional
    public void rejectFriendRequest(BeFriendRequest beFriendRequest) {

        Friend friend = friendRepository.findByUserIdAndFriendId(
                        beFriendRequest.receiver(), beFriendRequest.requester())
                .orElseThrow(() -> new FriendNotFoundException(FriendErrorCode.FRIEND_NOT_FOUND));

        friendRepository.delete(friend);
    }
}