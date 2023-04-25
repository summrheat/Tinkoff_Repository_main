package ru.tinkoff.edu.java.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.entity.Links;
import ru.tinkoff.edu.java.repository.LinkRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JpaLinkService {

    @Autowired
    LinkRepository repository;

    @Transactional
    public Links save(Links link) {
        return repository.save(link);
    }

    @Transactional
    public void delete(Links link) {
        repository.delete(link);
    }

    @Transactional(readOnly = true)
    public List<Links> getAll() {
        return repository.findAll();
    }



}
