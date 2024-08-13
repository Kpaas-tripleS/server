package com.tripleS.server.user.exception.errorcode;

import com.tripleS.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    EMAIL_DUPLICATED(HttpStatus.BAD_REQUEST, "이미 이메일을 사용하는 사용자가 존재합니다."),
    NICKNAME_DUPLICATED(HttpStatus.BAD_REQUEST, "이미 닉네임을 사용하는 사용자가 존재합니다.");

    private final HttpStatus httpStatus;
    private final String message;

}