package ru.tinkoff.edu.java.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URISyntaxException;

public class GHController {
    Mono<String> getData(String path) throws URISyntaxException {
        WebClient client = WebClient.create();
        Mono<String> responseSpec = client.get()
  
                .uri("https://api.github.com/repos/summrheat/Tinkoff_Repository_main")
                .retrieve()
                .bodyToMono(String.class);
        return  responseSpec;
    }
}
