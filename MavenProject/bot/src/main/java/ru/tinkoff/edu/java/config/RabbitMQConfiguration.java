package ru.tinkoff.edu.java.config;


import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    @Bean
    public Queue addChat() {
        return new Queue("addChat", false);
    }
    @Bean
    public Queue deleteChat() {
        return new Queue("deleteChat", false);
    }
    @Bean
    public Queue track() {
        return new Queue("track", false);
    }
    @Bean
    public Queue untrack() {
        return new Queue("untrack", false);
    }
    @Bean
    public Queue list() {
        return new Queue("list", false);
    }
    @Bean
    public Queue update() {
        return new Queue("update", false);
    }

}
