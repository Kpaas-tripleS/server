package com.tripleS.server.friend.service;

import com.tripleS.server.friend.domain.Friend;
import com.tripleS.server.friend.dto.request.BeFriendRequest;
import com.tripleS.server.friend.dto.request.FriendRequestHandleRequest;
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
    public void sendFriendRequest(Long requesterId, BeFriendRequest beFriendRequest) {

        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
        User receiver = userRepository.findById(beFriendRequest.receiverId())
                .orElseThrow(() -> new FriendNotFoundException(FriendErrorCode.FRIEND_NOT_FOUND));

        friendRepository.save(beFriendRequest.toEntity(requester, receiver));
    }

    public List<BeFriendResponse> getFriendRequestList(Long userId) {


        List<Friend> friendsList = friendRepository.findByUserIdAndIsAcceptedFalse(userId);

        return friendsList.stream()
                .map(BeFriendResponse::from)
                .toList();
    }

    // 친구 신청 수락과 친구 신청 거절 기능
    @Transactional
    public void handleFriendRequest(Long userId, FriendRequestHandleRequest friendRequestHandleRequest) {

        friendRepository.findByUserIdAndFriendId(userId, friendRequestHandleRequest.requesterId())
                .ifPresentOrElse(
                        friend -> {
                            if (friendRequestHandleRequest.isAccepted()) {
                                friend.accept();
                            } else {
                                friendRepository.delete(friend);
                            }
                        },
                        () -> {
                            throw new FriendNotFoundException(FriendErrorCode.FRIEND_NOT_FOUND);
                        });
    }
}