package ru.tinkoff.edu.java.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.dto.AddLinkRequest;

@Component
public class Consumer {

    @RabbitListener(queues = "addChat")
    public void addChat(AddLinkRequest in) {
        System.err.println("Message read from myQueue : " + in);
    }
    @RabbitListener(queues = "deleteChat")
    public void deleteChat(AddLinkRequest in) {
        System.err.println("Message read from myQueue : " + in);
    }
    @RabbitListener(queues = "track")
    public void track(AddLinkRequest in) {
        System.err.println("Message read from myQueue : " + in);
    }
    @RabbitListener(queues = "untrack")
    public void untrack(AddLinkRequest in) {
        System.err.println("Message read from myQueue : " + in);
    }
/*    @RabbitListener(queues = "myQueue")
    public void listen(String in) {
        System.err.println("Message read from myQueue : " + in);
    }*/

}
