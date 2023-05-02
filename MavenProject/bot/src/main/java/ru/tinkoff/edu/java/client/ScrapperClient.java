package ru.tinkoff.edu.java.client;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.dto.LinkResponse;
import ru.tinkoff.edu.java.dto.ScrapperResponse;

import java.net.URI;
import java.net.URISyntaxException;

public class ScrapperClient {

    private final WebClient webClient;

    public ScrapperClient() {
        String gitHubBaseUrl = "http://localhost:8081";
        this.webClient = WebClient.create(gitHubBaseUrl);
    }


    public String addChat(Long id) {
/*        WebClient client = WebClient.create();
        String requestBody = "{\"name\":\"John\", \"age\":30}";

        client.post()
                .uri("https://example.com/api/endpoint")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();*/

        return webClient.post().uri("/tg-chat/{id}", id)
                .header("tg_chat_id", String.valueOf(id))
                .header("Content-Type", "application/json").retrieve()
                .bodyToMono(String.class).block();

    }

    public String deleteChat(Long id) {

        return webClient.post().uri("/tg-chat/{id}", id)
                .header("tg_chat_id", String.valueOf(id))
                .header("Content-Type", "application/json").retrieve()
                .bodyToMono(String.class).block();

    }

    public String addLink(Long id, String url) throws URISyntaxException {


        return webClient.post().uri("/links")
                .header("tg_chat_id", String.valueOf(id))
                .body(BodyInserters.fromValue(new LinkResponse(new URI(url))))
                .retrieve()
                .bodyToMono(String.class).block();

    }
    public ScrapperResponse deleteLink(Long id) {

        return webClient.get().uri("/links")
                .header("tg_chat_id", String.valueOf(id))
                .header("Content-Type", "application/json").retrieve()
                .bodyToMono(ScrapperResponse.class).block();

    }
    public ScrapperResponse getAllLinks(Long id) {

        return webClient.get().uri("/links")
                .header("tg_chat_id", String.valueOf(id))
                .header("Content-Type", "application/json").retrieve()
                .bodyToMono(ScrapperResponse.class).block();

    }
}
