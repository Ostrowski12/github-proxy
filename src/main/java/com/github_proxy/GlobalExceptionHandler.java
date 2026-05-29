package com.github_proxy;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException ex) {

        int statusCode = ex.getErrorCode().getStatus().value();
        String message = ex.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(statusCode, message);

        return ResponseEntity.status(statusCode).body(errorResponse);
    }
}
