package ru.tinkoff.edu.java.config;


import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
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
    Queue queue() {
        return QueueBuilder.nonDurable("track")
                .withArgument("x-dead-letter-exchange", "exchange")
                .withArgument("x-dead-letter-routing-key", "routKey" + ".dlq")
                .build();
    }
    @Bean
    public Queue list() {
        return new Queue("list", false);
    }
    @Bean
    public Queue listResponse() {
        return new Queue("listResponse", false);
    }
    @Bean
    public Queue update() {
        return new Queue("update", false);
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        return cachingConnectionFactory;
    }

}
