package com.tripleS.server.quiz.exception;


import com.tripleS.server.quiz.exception.BaseException;
import com.tripleS.server.quiz.exception.errorcode.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handleBaseException(BaseException ex) {
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex) {
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
    }
}
