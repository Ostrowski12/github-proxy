package com.github_proxy;

record GithubBranchesResponse(
    String name,
    CommitRecord commit
) {
}
