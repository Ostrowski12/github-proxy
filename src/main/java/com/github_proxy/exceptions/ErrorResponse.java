package com.github_proxy.exceptions;

public record ErrorResponse(
        int status,
        String message
) {
}
