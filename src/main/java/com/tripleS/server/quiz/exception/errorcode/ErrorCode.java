package com.tripleS.server.quiz.exception.errorcode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    RESOURCE_NOT_FOUND(404, "Resource not found"),
    INVALID_INPUT(400, "Invalid input"),
    INTERNAL_SERVER_ERROR(500, "Internal server error");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }


}
