package ru.tinkoff.edu.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.edu.java.config.ApplicationConfig;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {
public static void main(String[] args) {
        var ctx = SpringApplication.run(BotApplication.class, args);

        var c = ctx.getBean("BOT", Bot.class);
        new CreateBot(c.getTest());
        System.out.println(c.getTest());
        }

}
