package ru.tinkoff.edu.java.jdbc;

import ru.tinkoff.edu.java.client.GitHubClient;
import ru.tinkoff.edu.java.client.StackOverflowClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class JdbcLinkService implements LinkService {
    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/scrapper", "postgres","postgres");

    public JdbcLinkService() throws SQLException {
    }

    @Override
    public void addLink(int chatId, URI url) throws SQLException {
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
        if (str_url.contains("stackoverflow")) {
            System.err.println(new StackOverflowClient().fetchQuestion(Long.parseLong(str_url.split("/")[2])));
            System.err.println("SOW");
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
        statement.setInt(1, chatId);
        statement.setInt(2, linkId);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void deleteLink(int linkId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM chat_links WHERE link_id=?;");
        statement.setInt(1, linkId);
        statement.executeUpdate();
    }

    @Override
    public List<Link> findAllLinksById(int chatId) throws SQLException, URISyntaxException {
        List<Link> links = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM chat_links JOIN links ON chat_links.link_id = links.link_id WHERE chat_id = ?;");

        statement.setInt(1, chatId);
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
}
