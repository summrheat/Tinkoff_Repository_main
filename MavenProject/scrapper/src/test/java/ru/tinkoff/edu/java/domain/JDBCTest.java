package ru.tinkoff.edu.java.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.jdbc.Link;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;


public class JDBCTest {
//    @Autowired
//    private JpaLinkDao linkRepository;
//    @Autowired
//    private JpaChatDao chatRepository;
    @Autowired
    @Transactional
    //@Rollback
    void addLinkTest() throws URISyntaxException {
        Link newLink = new Link(1,new URI("123"), new Timestamp(System.currentTimeMillis()));
        //Assert.assertTrue(linkRepository.get(0).equals(newLink));
    }

    @Transactional
    //@Rollback
    void removeLinkTest() throws URISyntaxException {
        Link newLink = new Link(1, new URI("123"), new Timestamp(System.currentTimeMillis()));
    }
}
//        newLink.setId(0);
//        newLink.setChat_id(0);
//        newLink.setLink_url("asda");
//        linkRepository.save(newLink);
//        linkRepository.delete(newLink);
//        Assert.assertNull(linkRepository.get(0));
//    }
//    @Transactional
//    @Rollback
//    void addChatTest() {
//        Chat newChat = new Chat();
//        chatRepository.save(newChat);
//        Assert.assertTrue(chatRepository.get(0).equals(newChat));
//    }

//    @Transactional
//    @Rollback
//    void removeChatTest() {
//        Chat newChat = new Chat();
//        newChat.setId(0);
//        newChat.setChat_id(0);
//        chatRepository.save(newChat);
//        chatRepository.delete(newChat);
//        Assert.assertNull(chatRepository.get(0));
//    }
