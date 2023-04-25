package ru.tinkoff.edu.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.java.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
