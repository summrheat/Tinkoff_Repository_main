package ru.tinkoff.edu.java.jdbc;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

public interface LinkService {
    void addLink(Long chatId, URI url) throws SQLException;
    List<Link> findAllLinks() throws SQLException, URISyntaxException;

    void deleteLink(Long chatId, String link) throws SQLException;

    List<Link> findAllLinksById(Long chatId) throws SQLException, URISyntaxException;
}
