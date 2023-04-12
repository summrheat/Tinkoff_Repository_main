package ru.tinkoff.edu.java;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

import java.util.List;

public class BotUpdater implements UpdatesListener {
    TelegramBot bot;
    int updateid_fromComand = 0;
    public BotUpdater (TelegramBot bot){
        this.bot = bot;
    }

    @Override
    public int process(List<Update> updates) {
            updates.forEach(update ->{

                String msg = update.message().text();
                System.out.println(msg);


                if (msg == null) msg = "/help";

                switch (msg){
                    case "/start" -> {
                        bot.execute(new SendMessage(update.message().chat().id(), "start"));
                        updateid_fromComand = update.message().messageId();
                    }
                    case "/help" -> {
                        bot.execute(new SendMessage(update.message().chat().id(), "никто не поможет"));
                        updateid_fromComand = update.message().messageId();
                    }
                    case "/track" -> {
                        bot.execute(new SendMessage(update.message().chat().id(), "track"));
                        update.message().messageId();
                        updateid_fromComand = update.message().messageId();
                    }
                    case "/untrack" -> {
                        bot.execute(new SendMessage(update.message().chat().id(), "untrack"));
                        updateid_fromComand = update.message().messageId();
                    }
                    case "/list" -> {
                        bot.execute(new SendMessage(update.message().chat().id(), "list"));
                        updateid_fromComand = update.message().messageId();
                    }
                    default ->{
                        if (update.message().messageId() == updateid_fromComand + 2)
                            bot.execute(new SendMessage(update.message().chat().id(), "зарегали команду"));
                        else bot.execute(new SendMessage(update.message().chat().id(), "введена хрень"));
                    }

                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }
    }



