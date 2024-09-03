package com.tripleS.server.user.dto.response;

import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.domain.type.Grade;
import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String name,
        String nickname,
        String profile_image,
        Long win_count,
        Grade grade
) {
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .profile_image(user.getProfileImage())
                .win_count(user.getWin_count())
                .grade(user.getGrade())
                .build();
    }
}
