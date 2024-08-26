package com.tripleS.server.match.exception;

import com.tripleS.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MatchNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
}
