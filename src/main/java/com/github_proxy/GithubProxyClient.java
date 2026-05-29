package com.github_proxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
class GithubProxyClient {

    private final RestClient restClient;

    GithubProxyClient(@Value("${github.api.url:https://api.github.com}") String githubUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(githubUrl)
                .build();
    }

    List<GithubRepositoriesResponse> getUserRepositories(String username) {
        try {
            GithubRepositoriesResponse[] repos = restClient.get()
                    .uri("/users/{username}/repos", username)
                    .retrieve()
                    .body(GithubRepositoriesResponse[].class);

            return repos != null ? Arrays.asList(repos) : List.of();

        } catch (HttpClientErrorException.NotFound e) {

            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);
        }
    }

    List<GithubBranchesResponse> getRepositoryBranches(String username, String repositoryName) {
        try {
            GithubBranchesResponse[] branches = restClient.get()
                    .uri("/repos/{username}/{repo}/branches", username, repositoryName)
                    .retrieve()
                    .body(GithubBranchesResponse[].class);

            return branches != null ? Arrays.asList(branches) : List.of();

        } catch (HttpClientErrorException.NotFound e) {

            throw new ApplicationException(ErrorCode.BRANCH_NOT_FOUND);
        }
    }
}
