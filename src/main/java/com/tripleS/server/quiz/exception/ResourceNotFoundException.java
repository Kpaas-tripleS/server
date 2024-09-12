package com.tripleS.server.quiz.exception;


import com.tripleS.server.quiz.exception.errorcode.ErrorCode;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String message) {
        super(ErrorCode.RESOURCE_NOT_FOUND, message);
    }

}

