package ru.tinkoff.edu.java.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import ru.tinkoff.edu.java.client.GitHubClient;
import ru.tinkoff.edu.java.client.StackOverflowClient;
import ru.tinkoff.edu.java.dto.LinkUpdaterResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JdbcLinkService implements LinkService {
    RabbitTemplate rabbitTemplate;
    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/scrapper", "postgres","postgres");

    public JdbcLinkService() throws SQLException {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        this.rabbitTemplate = rabbitTemplate;
    }
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        return cachingConnectionFactory;
    }

    @Override
    public void addLink(Long chatId, URI url) throws SQLException {
        PreparedStatement statement;
        String str_url = url.toString();
        str_url = str_url.replace("//", "");
        Timestamp updatetime = null;
        if (str_url.contains("github")) {
            System.err.println("github");
            updatetime = Timestamp.valueOf(
                    new GitHubClient().fetchRepo(str_url.split("/")[1], str_url.split("/")[2]).pushedAt()
                            .atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
            System.err.println(new GitHubClient().fetchRepo(str_url.split("/")[1], str_url.split("/")[2]));
            System.err.println(updatetime);
        }
        else if (str_url.contains("stackoverflow")) {
            System.err.println(new StackOverflowClient().fetchQuestion(Long.parseLong(str_url.split("/")[2])));
            System.err.println("SOW");
        }else{

        }

        if (getLinkId(String.valueOf(url)) == 0){
            statement = connection.prepareStatement(
                    "INSERT INTO links (updated_at, updated_at_scrapper ,link_url) VALUES (?, ?, ?)");

            statement.setTimestamp(1, updatetime);
            statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            statement.setString(3, String.valueOf(url));
            statement.executeUpdate();
        }
        int linkId = getLinkId(String.valueOf(url));

        statement = connection.prepareStatement("INSERT INTO chat_links (chat_id,link_id) VALUES (?, ?)");
        statement.setLong(1, chatId);
        statement.setInt(2, linkId);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void deleteLink(Long chatId, String link) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete FROM chat_links using links WHERE links.link_url = ? and chat_id = ? ;");
        statement.setLong(2, chatId);
        statement.setString(1, link);
        statement.executeUpdate();
    }

    @Override
    public List<Link> findAllLinksById(Long chatId) throws SQLException, URISyntaxException {
        List<Link> links = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM chat_links JOIN links ON chat_links.link_id = links.link_id WHERE chat_id = ?;");

        statement.setLong(1, chatId);
        ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Timestamp updateTime = rs.getTimestamp(4);
                var a = updateTime.getTime();
                System.err.println(a - System.currentTimeMillis());
                System.err.println("rs = " + rs.getInt(1) + rs.getInt(2)
                        +  rs.getString(3) + rs.getTimestamp(4) + rs.getString(5));
                links.add(new Link(rs.getInt(3), new URI(rs.getString(6)), rs.getTimestamp(4), rs.getTimestamp(5)));
            }
        statement.close();
        return  links;
    }

    public List<Integer> findAllChatById(int linkId) throws SQLException, URISyntaxException {
        List<Integer> ids = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM chat_links WHERE link_id = ?;");

        statement.setInt(1, linkId);
        ResultSet rs = statement.executeQuery();

        while (rs.next()){
            System.err.println("rs = " + rs.getInt(1));
            ids.add(rs.getInt(1));
        }
        statement.close();
        return  ids;
    }

    @Override
    public List<Link> findAllLinks() throws SQLException, URISyntaxException {
        List<Link> links = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM links");

        ResultSet rs = statement.executeQuery();

        while (rs.next()){
            Timestamp updateTime = rs.getTimestamp(2);
            var a = updateTime.getTime();
            links.add(new Link(rs.getInt(1), new URI(rs.getString(4)), rs.getTimestamp(3), rs.getTimestamp(2)));
        }
        statement.close();
        return  links;
    }

    public int getLinkId(String url) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT link_id FROM links WHERE link_url = ?");
        statement.setString(1, String.valueOf(url));
        int ans = 0;
        ResultSet rs = statement.executeQuery();

        while (rs.next()){
            ans = rs.getInt(1);
        }
        return ans;
    }

    public void update() throws SQLException, URISyntaxException {
        List<Link> links = new JdbcLinkService().findAllLinks();
        links.forEach(link -> {
            //log.info(link.toString());
            //log.info("diff between updates "+String.valueOf(System.currentTimeMillis() - link.updateTimeAtScrap().getTime()));
            if (System.currentTimeMillis() - link.updateTimeAtScrap().getTime() >  60*1000){
                PreparedStatement statement;
                try {


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

                    //log.info(String.valueOf(updatetime.compareTo(link.updateTime())));

                    if (updatetime.compareTo(link.updateTime()) != 0){
                        var a = new JdbcLinkService().findAllChatById(link.id());

                        statement = connection.prepareStatement(
                                "UPDATE links SET updated_at = ? WHERE link_id = ?;");
                        statement.setTimestamp(1, updatetime);
                        statement.setInt(2, link.id());
                        statement.executeUpdate();
                        rabbitTemplate.convertAndSend("update", new LinkUpdaterResponse(link.id(), link.url(),"что-то обновилось", a.toArray(new Integer[0])));
                        //new BotClient().updateLink(link.id(), link.url(),"что-то обновилось" ,a.toArray(new Integer[0]));

                        System.err.println("NOT EQUALS TIME " + updatetime +" " + link.updateTime());

                    }
                    statement = connection.prepareStatement(
                            "UPDATE links SET updated_at_scrapper = ? WHERE link_id = ?;");
                    statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                    statement.setInt(2, link.id());
                    statement.executeUpdate();
                    log.info(statement.toString());


                } catch (SQLException | URISyntaxException e) {
                    throw new RuntimeException(e);
                }
                //log.info(link.toString());

            }// 1 hour
        });
    }
}
