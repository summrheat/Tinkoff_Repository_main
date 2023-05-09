package ru.tinkoff.edu.java.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.dto.AddLinkRequest;

@Component
@RequiredArgsConstructor
public class Producer {

    private final RabbitTemplate rabbitTemplate;

    public void addChat(Long id) {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend("addChat", id);
    }
    public void deleteChat(Long id) {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend("deleteChat", id);
    }
    public void trackLink(AddLinkRequest request) {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend("track", request.link());
    }
    public void untrackLink(AddLinkRequest request) {//под антрек нет смысла новый дто писать
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend("untrack", request.link());
    }

}
