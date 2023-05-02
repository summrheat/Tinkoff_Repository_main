package ru.tinkoff.edu.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.entity.Chat;
import ru.tinkoff.edu.java.repository.ChatRepository;

import java.util.List;

public class JpaChatService {
    @Autowired
    ChatRepository repository;

    @Transactional
    public Chat createChat(long id) {
        Chat chat = new Chat();
        return repository.save(chat);
    }

    @Transactional
    public void deletePlayer(Long id) {
        Chat chat = new Chat();
        chat.setId(id);
        repository.delete(chat);
    }

    @Transactional(readOnly = true)
    public List<Chat> getAll() {
        return repository.findAll();
    }
}
