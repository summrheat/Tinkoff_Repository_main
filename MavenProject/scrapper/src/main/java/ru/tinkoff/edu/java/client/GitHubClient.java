package ru.tinkoff.edu.java.client;

import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.dto.GitHubResponse;

public class GitHubClient {

    private final WebClient webClient;

    //для использования baseUrl по умолчанию (берётся из properties)
    public GitHubClient() {
        String gitHubBaseUrl = "https://api.github.com";
        this.webClient = WebClient.create(gitHubBaseUrl);
    }


    //можно указать базовый URL
    public GitHubClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }


    public GitHubResponse fetchRepo(String owner, String repo) {

        return webClient.get().uri("/repos/{owner}/{repo}", owner, repo).retrieve()
                .bodyToMono(GitHubResponse.class).block();

    }
}
