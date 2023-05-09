package ru.tinkoff.edu.java.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.dto.AddLinkRequest;
import ru.tinkoff.edu.java.dto.LinkResponse;
import ru.tinkoff.edu.java.dto.ListLinksResponse;
import ru.tinkoff.edu.java.jdbc.JdbcLinkService;
import ru.tinkoff.edu.java.jdbc.Link;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Consumer {

    RabbitTemplate rabbitTemplate;
    public Consumer(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        this.rabbitTemplate = rabbitTemplate;
    }
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        return cachingConnectionFactory;
    }

    @RabbitListener(queues = "addChat")
    public void addChat(String id) {
        System.err.println("Message read from add : " + id);
    }
    @RabbitListener(queues = "deleteChat")
    public void deleteChat(Long id) {
        System.err.println("Message read from delete : " + id);
    }
    @RabbitListener(queues = "track")
    public void track(@Payload AddLinkRequest in, @Header("chatId") Long id) {
        System.err.println("Message read from track : " + in + " " + id);
        try {
            new JdbcLinkService().addLink(id, in.link());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @RabbitListener(queues = "untrack")
    public void untrack(@Payload AddLinkRequest in, @Header("chatId") Long id) {
        System.err.println("Message read from untrack : " + in);
        try {
            new JdbcLinkService().deleteLink(id, in.link().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = "list")
    public void list(@Header("chatId") Long id) throws JsonProcessingException {
        System.err.println("Message read from list : " + id);
        List<LinkResponse> links = new ArrayList<>();
        try {
            List<Link> list = new JdbcLinkService().findAllLinksById(id);
            list.forEach(link -> links.add(new LinkResponse(link.id(), link.url())));
        } catch (SQLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        ListLinksResponse response = new ListLinksResponse(links.size(), links);

        System.err.println("LINKS "+response);

        MessageProperties properties = new MessageProperties();
        properties.setHeader("__TypeId__", "ru.tinkoff.edu.dto.ListLinksResponse");
        properties.setHeader("chatId", id);
        properties.setContentEncoding("UTF-8");
        properties.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        Message message = new Message(objectMapper.writeValueAsBytes(response), properties);

        System.out.println(message.toString());

        rabbitTemplate.convertAndSend("listResponse", message);
    }

/*    @RabbitListener(queues = "myQueue")
    public void listen(String in) {
        System.err.println("Message read from myQueue : " + in);
    }*/

}
