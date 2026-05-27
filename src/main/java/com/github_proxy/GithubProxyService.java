package com.github_proxy;

import com.github_proxy.records.BranchRecord;
import com.github_proxy.records.RepositoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class GithubProxyService {

    private final GithubProxyClient githubProxyClient;

    GithubProxyService(GithubProxyClient githubProxyClient) {
        this.githubProxyClient = githubProxyClient;
    }

    public List<RepositoryDTO> getRepos(String username) {

      var repositories = githubProxyClient.getUserRepositories(username);

        return repositories.stream()
                .filter(repo -> !repo.fork())
                .map(repo -> {
                    List<BranchRecord> branches = githubProxyClient.getRepositoryBranches(username, repo.name())
                            .stream()
                            .map(branch -> new BranchRecord(branch.name(), branch.commit().sha()))
                            .toList();

                    return new RepositoryDTO(repo.name(), username, branches);
                })
                .toList();

    };


}
