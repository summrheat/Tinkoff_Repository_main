package ru.tinkoff.edu.java;

import ru.tinkoff.edu.java.data.GitHub;
import ru.tinkoff.edu.java.data.StackOverFlow;
import ru.tinkoff.edu.java.parsers.GitHubParser;
import ru.tinkoff.edu.java.parsers.StackOverFlowParser;

public class ParserURL {
    public void parse(String link) {
        try {

            link = link.strip();

            if (link.contains("github.com")) {
                String data = new GitHubParser().parse(link);
                GitHub response = new GitHub(data.split(":")[0],data.split(":")[1]);
                System.out.println(response);
            }
            else if (link.contains("stackoverflow.com")) {
                String data = new StackOverFlowParser().parse(link);
                StackOverFlow response = new StackOverFlow(data);
                System.out.println(response);
            }else {
                throw new RuntimeException("something wrong");
            }

        }catch (Exception e){
            throw new RuntimeException("something wrong");
        }

    }

    public static void main(String[] args) {
        new ParserURL().parse("https://github.com/sanyarnd/tinkoff-java-course-2022/");
    }

}
