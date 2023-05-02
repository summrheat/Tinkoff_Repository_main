package ru.tinkoff.edu.java.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.client.GitHubClient;
import ru.tinkoff.edu.java.client.StackOverflowClient;
import ru.tinkoff.edu.java.dto.LinkUpdaterResponse;
import ru.tinkoff.edu.java.jdbc.JdbcLinkService;
import ru.tinkoff.edu.java.jdbc.Link;

import java.net.URISyntaxException;
import java.sql.*;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkUpdateScheduler {
    private final RabbitTemplate rabbitTemplate;


    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() throws SQLException, URISyntaxException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/scrapper", "postgres","postgres");


        List<Link> links = new JdbcLinkService().findAllLinks();
        links.forEach(link -> {
            log.info(link.toString());
            log.info("diff between updates "+String.valueOf(System.currentTimeMillis() - link.updateTimeAtScrap().getTime()));
            if (System.currentTimeMillis() - link.updateTimeAtScrap().getTime() >  60*1000){
                PreparedStatement statement;
                try {

                    statement = connection.prepareStatement(
                            "UPDATE links SET updated_at_scrapper = ? WHERE link_id = ?;");
                    statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                    statement.setInt(2, link.id());
                    log.info(statement.toString());


                    String url = link.url().toString().replace("//", "");
                    Timestamp updatetime = null;
                    if (url.contains("github")) {
                        System.err.println("github");
                        updatetime = Timestamp.valueOf(
                                new GitHubClient().fetchRepo(url.split("/")[1], url.split("/")[2]).pushedAt()
                                        .atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
                        System.err.println(new GitHubClient().fetchRepo(url.split("/")[1], url.split("/")[2]));
                        System.err.println(updatetime);
                    }
                    if (url.contains("stackoverflow")) {
                        System.err.println(new StackOverflowClient().fetchQuestion(Long.parseLong(url.split("/")[2])));
                        System.err.println("SOW");
                    }

                    log.info(String.valueOf(updatetime.compareTo(link.updateTime())));

                    if (updatetime.compareTo(link.updateTime()) == 0){
                        System.err.println("EQUALS TIME");
                        var a = new JdbcLinkService().findAllChatById(link.id());
                        //new Producer(new RabbitTemplate()).sendUpdate(link.id(), link.url(),"что-то обновилось", a.toArray(new Integer[0]));

                        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                        rabbitTemplate.convertAndSend("update", new LinkUpdaterResponse(link.id(), link.url(),"что-то обновилось", a.toArray(new Integer[0])));

                        //new BotClient().updateLink(link.id(), link.url(),"что-то обновилось", a.toArray(new Integer[0]));
                    }else{
                        var a = new JdbcLinkService().findAllChatById(link.id());
                        System.err.println(a.toArray(new Integer[0]));
                        //new BotClient().updateLink(link.id(), link.url(),"что-то обновилось" ,a.toArray(new Integer[0]));
                        System.err.println("NOT EQUALS TIME " + updatetime +" " + link.updateTime());
                    }



                    statement.executeUpdate();

                   /* statement = connection.prepareStatement(
                            "SELECT chat_links SET updated_at = ? WHERE link_id = ?;");
                    statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                    statement.setInt(2, link.id());
                    log.info(statement.toString());
                    statement.executeUpdate();*/
                    //new BotClient().updateLink(link.id(), link.url());
                } catch (SQLException | URISyntaxException e) {
                    throw new RuntimeException(e);
                }
                log.info(link.toString());

            }// 1 hour
        });
    }
}
