package ru.tinkoff.edu.java.dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LinkController implements LinkDAO {
    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/scrapper", "postgres","postgres");

    public LinkController() throws SQLException {
    }

    @Override
    public void addLink(int chatId, URI url) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO links (link_url) VALUES (?)");
        statement.setString(1, String.valueOf(url));
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void deleteLink(int chatId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM links");
    }

    @Override
    public List<Link> findAllLinks(int chatId) throws SQLException, URISyntaxException {
        List<Link> links = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM links");
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                links.add(new Link(rs.getInt(1), new URI(rs.getString(2))));
            }
        statement.close();
        return  links;
    }
}
