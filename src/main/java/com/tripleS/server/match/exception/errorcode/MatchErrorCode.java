package com.tripleS.server.match.exception.errorcode;

import com.tripleS.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MatchErrorCode implements ErrorCode {
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST, "해당 회원이 존재하지 않습니다."),
    MATCH_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 대결을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
