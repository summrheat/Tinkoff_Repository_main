package ru.tinkoff.edu.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class ParserTest {
    static ParserURL parser;
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        parser = new ParserURL();
    }
    @Test
    void testStackUnsup()
    {
        System.out.println("======TEST ONE EXECUTED=======");
        Assertions.assertThrows(RuntimeException.class, () ->{
                    parser.parse("https://stackoverflow.com/search?q=unsupported%20link");
                });
    }
    @Test
    void testStackSupp()
    {
            parser.parse("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");
    }
    @Test
    void testGitSupp()
    {
            parser.parse("https://github.com/sanyarnd/tinkoff-java-course-2022");
    }

}
