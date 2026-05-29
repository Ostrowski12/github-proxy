package com.github_proxy;

import org.springframework.http.HttpStatus;

enum ErrorCode {

    USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
    BRANCH_NOT_FOUND("Branch not found", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
