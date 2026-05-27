package com.github_proxy.responses;

import com.github_proxy.records.CommitRecord;

public record GithubBranchesResponse(
    String name,
    CommitRecord commit
) {
}
