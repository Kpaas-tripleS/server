package com.tripleS.server.user.dto.response;

import com.tripleS.server.user.domain.User;

public record GetUserInfoResponse(
        String nickname,
        String password,
        String profileImage,
        String phoneNumber
) {
    public static GetUserInfoResponse of(User user) {
        return new GetUserInfoResponse(
                user.getNickname(),
                user.getPassword(),
                user.getProfileImage(),
                user.getPhone());
    }
}
