package ru.tinkoff.edu.java.jdbc;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

public interface LinkService {
    void addLink(int chatId, URI url) throws SQLException;
    void deleteLink(int chatId) throws SQLException;
    List<Link> findAllLinks() throws SQLException, URISyntaxException;
    List<Link> findAllLinksById(int chatId) throws SQLException, URISyntaxException;
}
