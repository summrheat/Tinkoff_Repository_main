package ru.tinkoff.edu.java.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.dao.JdbcLinkService;
import ru.tinkoff.edu.java.dao.Link;

import java.net.URISyntaxException;
import java.sql.*;
import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Component
public class LinkUpdateScheduler {


    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() throws SQLException, URISyntaxException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/scrapper", "postgres","postgres");

        log.info(OffsetDateTime.now().toString());
        List<Link> links = new JdbcLinkService().findAllLinks();
        links.forEach(link -> {
            log.info("jyguifa "+String.valueOf(System.currentTimeMillis() - link.updateTime().getTime()));
            if (System.currentTimeMillis() - link.updateTime().getTime() > 10*60*1000){
                PreparedStatement statement;
                try {

                    statement = connection.prepareStatement(
                            "UPDATE links SET updated_at = ? WHERE link_id = ?;");
                    statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                    statement.setInt(2, link.id());
                    log.info(statement.toString());
                    statement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                log.info(link.toString());

            }// 1 hour
        });
    }
}
