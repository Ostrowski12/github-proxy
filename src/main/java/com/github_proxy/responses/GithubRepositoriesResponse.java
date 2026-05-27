package com.github_proxy.responses;

public record GithubRepositoriesResponse(
        String name,
        Boolean fork
) {
}
