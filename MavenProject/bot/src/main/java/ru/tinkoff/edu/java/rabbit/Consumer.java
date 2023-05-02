package ru.tinkoff.edu.java.rabbit;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.CreateBot;
import ru.tinkoff.edu.java.dto.LinkUpdaterRequest;

import java.util.Arrays;

@Component
public class Consumer {

    @RabbitListener(queues = "update")
    public void listen(LinkUpdaterRequest request) {
        TelegramBot bot = CreateBot.getBot();
        System.err.println("from rabbit " + Arrays.toString(request.tgChatIds()));
        for (Integer chatid : request.tgChatIds()) {
            bot.execute(new SendMessage(chatid, "По вашей ссылке  " + request.url() + " произошло обновление "
                    + request.description()));
        }
    }
    @RabbitListener(queues = "myQueue")
    public void listen(String in) {
        System.err.println("Message read from myQueue : " + in);
    }

}
