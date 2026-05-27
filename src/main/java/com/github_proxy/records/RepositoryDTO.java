package com.github_proxy.records;

import java.util.List;

public record RepositoryDTO(
        String repositoryName,
        String owner,
        List<BranchRecord> branches
) {
}
