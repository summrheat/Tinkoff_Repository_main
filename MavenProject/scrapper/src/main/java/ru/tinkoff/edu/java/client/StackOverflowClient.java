package ru.tinkoff.edu.java.client;


import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.dto.StackOverflowItem;
import ru.tinkoff.edu.java.dto.StackOverflowResponse;

public class StackOverflowClient {

    private final WebClient webClient;


    //для использования baseUrl по умолчанию (берётся из properties)
    public StackOverflowClient() {
        String stackOverflowBaseUrl = "https://api.stackexchange.com/2.3/";
        this.webClient = WebClient.create(stackOverflowBaseUrl);
    }


    //можно указать базовый URL
    public StackOverflowClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    public StackOverflowItem fetchQuestion(long id) {

        StackOverflowResponse response = webClient.get().uri("/questions/{id}?order=desc&sort=activity&site=stackoverflow", id)
                .retrieve()
                .bodyToMono(StackOverflowResponse.class)
                .block();

        assert response != null;
        return response.items().get(0);
    }
}
