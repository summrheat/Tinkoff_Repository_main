package ru.tinkoff.edu.java;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;


public class CreateBot {
    static TelegramBot bot;
    BotCommand[] commands = {
            new BotCommand("start", "зарегестрировать пользователя"),
            new BotCommand("help", "вывести окно с командами"),
            new BotCommand("track", "начать отслеживание ссылки"),
            new BotCommand("untrack", "прекратить отслеживание ссылки"),
            new BotCommand("list", "показать список отслеживаемых ссылок")
    };
    CreateBot(String token){
        bot = new TelegramBot(token);
        bot.execute(new SetMyCommands(commands));
        bot.setUpdatesListener(new BotUpdater(bot));
    }

    public static TelegramBot getBot(){
        return bot;
    }

}
