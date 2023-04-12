package ru.tinkoff.edu.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.tinkoff.edu.java.client.StackOverflowClient;
import ru.tinkoff.edu.java.configuration.ApplicationConfig;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(ApplicationConfig.class)
public class ScrapperApplication {
        public static void main(String[] args) {
                var ctx = SpringApplication.run(ScrapperApplication.class, args);
                //ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
                //System.out.println(config);
        }
}