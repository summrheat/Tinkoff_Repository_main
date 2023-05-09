package ru.tinkoff.edu.java.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.jdbc.JdbcLinkService;

import java.net.URISyntaxException;
import java.sql.SQLException;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkUpdateScheduler {
    private final RabbitTemplate rabbitTemplate;
    JdbcLinkService service;

    {
        try {
            service = new JdbcLinkService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() throws SQLException, URISyntaxException {
        //Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/scrapper", "postgres","postgres");

        service.update();

    }
}
