package com.tripleS.server.friend.exception;

import com.tripleS.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FriendNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;
}
