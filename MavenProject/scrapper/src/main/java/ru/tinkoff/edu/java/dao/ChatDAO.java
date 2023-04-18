package ru.tinkoff.edu.java.dao;

import java.sql.SQLException;

public interface ChatDAO {
    void addChat(int chatId) throws SQLException;
    void deleteChat(int chatId) throws SQLException;
}
