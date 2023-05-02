package ru.tinkoff.edu.java;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.client.ScrapperClient;

import java.net.URISyntaxException;
import java.util.List;

public class BotUpdater implements UpdatesListener {
    String comand;
    TelegramBot bot;
    int updateid_fromComand = 0;
    ScrapperClient client = new ScrapperClient();
    public BotUpdater (TelegramBot bot){
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
                    bot.execute(new SendMessage(update.message().chat().id(), "список команд: \\n1.start \\n2.help \\n3.track \\n4.untrack \\n5.list"));
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
                    bot.execute(new SendMessage(update.message().chat().id(), "list"));
                }
                default ->{
                    if (update.message().messageId() == updateid_fromComand + 2){
                        switch (comand){
                            case "/track" -> {
                                try {
                                    client.addLink(update.message().chat().id(), update.message().text());
                                } catch (URISyntaxException e) {
                                    throw new RuntimeException(e);
                                }
                                bot.execute(new SendMessage(update.message().chat().id(), "track"));

                            }
                            case "/untrack" -> bot.execute(new SendMessage(update.message().chat().id(), "untrack"));

                        }

                    }
                    else bot.execute(new SendMessage(update.message().chat().id(), "введена информация"));
                }

            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    void start(Update update){

        new ScrapperClient().addChat(update.message().chat().id());
        bot.execute(new SendMessage(update.message().chat().id(), "зарегали команду старт"));
    }

}



