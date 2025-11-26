package com.tjoeun.corunning.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginRequiredException.class)
    public ResponseEntity<String> handleLoginRequired(LoginRequiredException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<String> handleUnauthorized(UnauthorizedAccessException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    // 기타 예외 핸들러도 추가 가능
}
