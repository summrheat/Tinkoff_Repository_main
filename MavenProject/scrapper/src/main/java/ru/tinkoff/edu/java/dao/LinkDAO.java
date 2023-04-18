package ru.tinkoff.edu.java.dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

public interface LinkDAO {

    void addLink(int chatId, URI url) throws SQLException;

    void deleteLink(int chatId) throws SQLException;
    List<Link> findAllLinks(int chatId) throws SQLException, URISyntaxException;
}
