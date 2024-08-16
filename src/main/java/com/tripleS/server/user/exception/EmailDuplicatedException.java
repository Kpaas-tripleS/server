package com.tripleS.server.user.exception;

import com.tripleS.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmailDuplicatedException extends RuntimeException {

    private final ErrorCode errorCode;
}
