package ru.tinkoff.edu.java.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.jdbc.Link;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;


public class JDBCTest {

    @Autowired
    @Transactional
    //@Rollback
    void addLinkTest() throws URISyntaxException {
        Link newLink = new Link(1,new URI("123"), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        //Assert.assertTrue(linkRepository.get(0).equals(newLink));
    }

    @Transactional
        //@Rollback
    void removeLinkTest() throws URISyntaxException {
        Link newLink = new Link(1, new URI("123"), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
    }
}
