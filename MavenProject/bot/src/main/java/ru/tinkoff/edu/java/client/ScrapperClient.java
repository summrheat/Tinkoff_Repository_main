package ru.tinkoff.edu.java.client;

import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.dto.ScrapperResponse;

public class ScrapperClient {

    private final WebClient webClient;

    public ScrapperClient() {
        String gitHubBaseUrl = "http://localhost:8081";
        this.webClient = WebClient.create(gitHubBaseUrl);
    }


    public ScrapperResponse addChat(Integer id) {

        return webClient.get().uri("/tg-chat/{id}", id)
                .header("tg_chat_id", "12")
                .header("Content-Type", "application/json").retrieve()
                .bodyToMono(ScrapperResponse.class).block();

    }
    public ScrapperResponse links() {

        return webClient.get().uri("/links")
                .header("tg_chat_id", "12")
                .header("Content-Type", "application/json").retrieve()
                .bodyToMono(ScrapperResponse.class).block();

    }
}
