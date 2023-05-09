package ru.tinkoff.edu.java.client;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.dto.LinkUpdaterResponse;

import java.net.URI;
import java.net.URISyntaxException;

public class BotClient {
    private final WebClient webClient;

    //для использования baseUrl по умолчанию (берётся из properties)
    public BotClient() {
        String gitHubBaseUrl = "http://localhost:8080";
        this.webClient = WebClient.create(gitHubBaseUrl);
    }


    //можно указать базовый URL
    public BotClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }


    public String updateLink(Integer id, URI url,String description, Integer[] ids) throws URISyntaxException {

        return webClient.post().uri("/updates")
                .header("Content-Type", "application/json")
                .body(BodyInserters.fromValue(new LinkUpdaterResponse(id, url, description, ids)))
                //.body(new LinkUpdaterResponse(id, url, description, new Integer[]{431986570}), LinkUpdaterResponse.class)
                .retrieve()
                .bodyToMono(String.class).block();

    }
}
