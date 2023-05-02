package ru.tinkoff.edu.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.java.entity.Links;

public interface LinkRepository extends JpaRepository<Links, Long> {
}
