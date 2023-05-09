package ru.tinkoff.edu.java;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import ru.tinkoff.edu.java.dto.AddLinkRequest;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;

public class BotUpdater implements UpdatesListener {

    String comand;
    TelegramBot bot;
    int updateid_fromComand = 0;
    //ScrapperClient client = new ScrapperClient();
    RabbitTemplate rabbitTemplate;



    public BotUpdater (TelegramBot bot){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        this.rabbitTemplate = rabbitTemplate;
        this.bot = bot;
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update ->{

            String msg = update.message().text();
            System.out.println(update.message().chat().username() + " " + msg + update.message().chat().id());


            if (msg == null) msg = "/help";

            switch (msg){
                case "/start" -> {
                    start(update);
                }
                case "/help" -> {
                    bot.execute(new SendMessage(update.message().chat().id(), "список команд: \\\\n1.start \\\\n2.help \\\\n3.track \\\\n4.untrack \\\\n5.list"));
                }
                case "/track" -> {
                    bot.execute(new SendMessage(update.message().chat().id(), "type link"));
                    update.message().messageId();
                    updateid_fromComand = update.message().messageId();
                    comand = msg;
                }
                case "/untrack" -> {
                    bot.execute(new SendMessage(update.message().chat().id(), "type link"));
                    updateid_fromComand = update.message().messageId();
                    comand = msg;
                }
                case "/list" -> {
                    MessageProperties properties = new MessageProperties();
                    properties.setHeader("chatId", update.message().chat().id());
                    properties.setContentEncoding("UTF-8");
                    Message message = new Message("123".getBytes(), properties);
                    rabbitTemplate.convertAndSend("list", message);
                }
                default ->{
                    if (update.message().messageId() == updateid_fromComand + 2){
                        switch (comand){
                            case "/track" -> {

                                if (update.message().text().contains("github") ||
                                        update.message().text().contains("stackoverflow")){
                                    try {
                                        //rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

                                        MessageProperties properties = new MessageProperties();
                                        properties.setHeader("__TypeId__", "ru.tinkoff.edu.java.dto.AddLinkRequest");
                                        properties.setHeader("chatId", update.message().chat().id());
                                        properties.setContentEncoding("UTF-8");
                                        properties.setContentType("application/json");
                                        ObjectMapper objectMapper = new ObjectMapper();
                                        Message message = new Message(objectMapper.writeValueAsBytes(new AddLinkRequest(
                                                new URI(update.message().text()))), properties);

                                        System.out.println(message.toString());
                                        rabbitTemplate.convertAndSend("track", message);
                                        bot.execute(new SendMessage(update.message().chat().id(),
                                                "Ваша ссылка добавлена для отслеживания"));
                                    } catch (URISyntaxException | JsonProcessingException e) {
                                        throw new RuntimeException(e);
                                    }


                                }else{
                                    bot.execute(new SendMessage(update.message().chat().id(),
                                            "Данный тип ссылок не поддерживается"));
                                }

                                //client.addLink(update.message().chat().id(), update.message().text());


                            }
                            case "/untrack" -> {
                                try {
                                    MessageProperties properties = new MessageProperties();
                                    properties.setHeader("__TypeId__", "ru.tinkoff.edu.java.dto.AddLinkRequest");
                                    properties.setHeader("chatId", update.message().chat().id());
                                    properties.setContentEncoding("UTF-8");
                                    properties.setContentType("application/json");
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    Message message = new Message(objectMapper.writeValueAsBytes(new AddLinkRequest(
                                            new URI(update.message().text()))), properties);

                                    System.out.println(message.toString());
                                    rabbitTemplate.convertAndSend("untrack", message);
                                    bot.execute(new SendMessage(update.message().chat().id(),
                                            "Ваша ссылка удалена из отслеживания"));
                                } catch (URISyntaxException | JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }

                            }

                        }

                    }
                    else bot.execute(new SendMessage(update.message().chat().id(), "Такой команды нет"));
                }

            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    void start(Update update){


        rabbitTemplate.convertAndSend("addChat", update.message().chat().id());

        //producer.addChat(update.message().chat().id());
        //new ScrapperClient().addChat(update.message().chat().id());
        bot.execute(new SendMessage(update.message().chat().id(), "зарегали команду старт"));
    }

    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        return cachingConnectionFactory;
    }

}



