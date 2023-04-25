package ru.tinkoff.edu.java.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcChatService implements ChatService {
    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/scrapper", "postgres","postgres");

    public JdbcChatService() throws SQLException {
    }

    @Override
    public void addChat(int chatId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO chats (chat_id) VALUES (?)");
        statement.setInt(1, chatId);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void deleteChat(int chatId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM chat_links WHERE chat_id=?;");
        statement.setInt(1, chatId);
        statement.executeUpdate();
        statement = connection.prepareStatement("DELETE FROM chats WHERE chat_id=?;");
        statement.setInt(1, chatId);
        statement.executeUpdate();
        statement.close();
    }
}
