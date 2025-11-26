package com.tjoeun.corunning.exception;

public class LoginRequiredException extends RuntimeException {
    public LoginRequiredException(String message) {
        super(message);
    }
}

