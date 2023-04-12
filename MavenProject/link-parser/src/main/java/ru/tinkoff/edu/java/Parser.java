package ru.tinkoff.edu.java;

import ru.tinkoff.edu.java.data.GitHub;
import ru.tinkoff.edu.java.data.StackOverFlow;
import ru.tinkoff.edu.java.parsers.GitHubParser;
import ru.tinkoff.edu.java.parsers.StackOverFlowParser;

import java.util.Scanner;

public class Parser {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("type URL");
            String test = sc.nextLine();
            test = test.strip();

            if (test.contains("github.com")) {
                String data = new GitHubParser().parse(test);
                GitHub response = new GitHub(data.split(":")[0],data.split(":")[1]);
                System.out.println(response);
            }
            if (test.contains("stackoverflow.com")) {
                String data = new StackOverFlowParser().parse(test);
                StackOverFlow response = new StackOverFlow(data);
                System.out.println(response);
            }

        }catch (Exception e){
            System.err.println("SOMETHING WRONG");
        }

    }
}
//        https://github.com/sanyarnd/tinkoff-java-course-2022/
//        https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c
//        https://stackoverflow.com/search?q=unsupported%20link