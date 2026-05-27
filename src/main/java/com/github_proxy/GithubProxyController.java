package com.github_proxy;

import com.github_proxy.records.RepositoryDTO;
import org.springframework.web.bind.annotation.*;

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
