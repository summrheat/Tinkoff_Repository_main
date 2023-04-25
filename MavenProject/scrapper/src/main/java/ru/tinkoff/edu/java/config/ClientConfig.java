package ru.tinkoff.edu.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.client.GitHubClient;
import ru.tinkoff.edu.java.client.StackOverflowClient;

@Configuration
public class ClientConfig {

    @Bean
    public GitHubClient gitHubClientService() {
        return new GitHubClient();
    }

    @Bean
    public StackOverflowClient stackOverflowClientService() {
        return new StackOverflowClient();
    }

}
