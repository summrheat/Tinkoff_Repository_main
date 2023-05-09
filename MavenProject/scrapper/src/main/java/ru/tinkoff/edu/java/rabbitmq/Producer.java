package ru.tinkoff.edu.java.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.dto.LinkResponse;
import ru.tinkoff.edu.java.dto.LinkUpdaterResponse;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rabbit")
public class Producer {

    private final RabbitTemplate rabbitTemplate;


    public void sendUpdate(Integer id, URI url, String description, Integer[] ids) {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend("update", new LinkUpdaterResponse(id, url, description, ids));
    }


    @PostMapping
    public void sendMessage(@RequestBody LinkResponse request) {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend("myQueue", request);
    }
}
