package ru.tinkoff.edu.java.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URISyntaxException;

@RequestMapping(value = "/123")
@RestController
public class GHController {
    @GetMapping
    Mono<String> getAllLinks() throws URISyntaxException {
        WebClient client = WebClient.create();

        Mono<String> responseSpec = client.get()
                .uri("https://api.github.com/repos/TrofimTyulkin/tinkoff-education")
                .retrieve()
                .bodyToMono(String.class);
        return  responseSpec;
    }
}
