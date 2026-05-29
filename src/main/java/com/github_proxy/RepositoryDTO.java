package com.github_proxy;

import java.util.List;

record RepositoryDTO(
        String repositoryName,
        String owner,
        List<BranchRecord> branches
) {
}
