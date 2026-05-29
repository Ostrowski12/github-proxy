package com.github_proxy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/githubProxy")
class GithubProxyController {

    private final GithubProxyService githubProxyService;

    GithubProxyController(GithubProxyService githubProxyService) {
        this.githubProxyService = githubProxyService;
    }

    @GetMapping("/{username}")
    List<RepositoryDTO> getGithubData(@PathVariable String username) {
        return githubProxyService.getRepos(username);
    }

}
