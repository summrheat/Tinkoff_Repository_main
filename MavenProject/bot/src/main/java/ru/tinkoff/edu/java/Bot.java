package ru.tinkoff.edu.java;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
import org.springframework.beans.factory.annotation.Value;

public final class Bot {

    @Value("{$bot.name}")
    private String BOT_NAME;

    @Value("${app.token}")
    private String BOT_TOKEN;



    public Bot() {
        System.out.println("+++++++TOKEN "+BOT_TOKEN);
        TelegramBot bot = new TelegramBot(BOT_TOKEN);

        BotCommand[] commands = {
                new BotCommand("start", "зарегестрировать пользователя"),
                new BotCommand("help", "вывести окно с командами"),
                new BotCommand("track", "начать отслеживание ссылки"),
                new BotCommand("untrack", "прекратить отслеживание ссылки"),
                new BotCommand("list", "показать список отслеживаемых ссылок")
        };

        bot.execute(new SetMyCommands(commands));
        bot.setUpdatesListener(new BotUpdater(bot));

    }
}
