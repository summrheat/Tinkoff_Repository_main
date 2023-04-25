package ru.tinkoff.edu.java.dao;

import java.sql.SQLException;

public interface ChatService {
    void addChat(int chatId) throws SQLException;
    void deleteChat(int chatId) throws SQLException;
}
